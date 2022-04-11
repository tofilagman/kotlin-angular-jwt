package org.study.sharedrive.respository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.study.sharedrive.model.ERole
import org.study.sharedrive.model.Role
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: ERole): Optional<Role>;
}