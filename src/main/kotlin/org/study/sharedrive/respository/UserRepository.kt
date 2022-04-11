package org.study.sharedrive.respository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.study.sharedrive.model.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>;
    fun existsByUsername(username: String): Boolean;
    fun existsByEmail(email: String): Boolean;
}