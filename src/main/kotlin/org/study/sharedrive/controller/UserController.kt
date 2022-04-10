package org.study.sharedrive.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.study.sharedrive.model.User
import org.study.sharedrive.respository.UserRepository

@RestController
@RequestMapping("/v1/")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("users")
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}