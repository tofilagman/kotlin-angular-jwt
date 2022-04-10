package org.study.sharedrive.respository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.study.sharedrive.model.User

@Repository
interface UserRepository: JpaRepository<User, Long> {

}