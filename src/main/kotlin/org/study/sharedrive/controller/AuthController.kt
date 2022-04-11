package org.study.sharedrive.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.study.sharedrive.model.ERole
import org.study.sharedrive.model.Role
import org.study.sharedrive.model.User
import org.study.sharedrive.payload.request.LoginRequest
import org.study.sharedrive.payload.request.SignupRequest
import org.study.sharedrive.payload.response.JwtResponse
import org.study.sharedrive.payload.response.MessageResponse
import org.study.sharedrive.respository.RoleRepository
import org.study.sharedrive.respository.UserRepository
import org.study.sharedrive.security.jwt.JwtUtils
import org.study.sharedrive.security.services.UserDetailsImpl
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val jwt = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { it.authority }
            .collect(Collectors.toList())

        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.email,
                roles
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: SignupRequest): ResponseEntity<*>? {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Email is already in use!"))
        }
        // Create new user's account
        val user = User(
            signUpRequest.username,
            signUpRequest.email,
            encoder.encode(signUpRequest.password)
        )

        val strRoles: Set<String> = signUpRequest.role
        val roles: MutableSet<Role> = HashSet<Role>()
        if (strRoles == null) {
            val userRole: Role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow { RuntimeException("Error: Role is not found.") }
            roles.add(userRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole: Role = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        roles.add(adminRole)
                    }
                    "mod" -> {
                        val modRole: Role = roleRepository.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        roles.add(modRole)
                    }
                    else -> {
                        val userRole: Role = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow {
                                RuntimeException(
                                    "Error: Role is not found."
                                )
                            }
                        roles.add(userRole)
                    }
                }
            })
        }
        user.roles = roles
        userRepository.save<User>(user)
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}