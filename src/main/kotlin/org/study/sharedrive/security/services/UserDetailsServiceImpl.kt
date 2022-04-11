package org.study.sharedrive.security.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.study.sharedrive.respository.UserRepository
import javax.transaction.Transactional

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val errorMessage = UsernameNotFoundException("User Not Found with username: $username")
        val user = username?.let { userRepository.findByUsername(it)
            .orElseThrow { errorMessage } }
            ?: throw errorMessage
        return UserDetailsImpl.build(user)
    }
}