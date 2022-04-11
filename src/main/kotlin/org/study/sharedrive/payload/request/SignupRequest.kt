package org.study.sharedrive.payload.request

data class SignupRequest (
    val username: String,
    val email: String,
    val password: String,
    val role: Set<String>
        )