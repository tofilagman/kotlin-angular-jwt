package org.study.sharedrive.payload.response

data class JwtResponse(
    val token: String,
    val id: Long,
    val username: String,
    val email: String,
    val roles: List<String>
)