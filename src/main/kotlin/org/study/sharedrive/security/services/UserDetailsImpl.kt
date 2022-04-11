package org.study.sharedrive.security.services

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.study.sharedrive.model.User
import java.util.stream.Collectors


class UserDetailsImpl : UserDetails {
    companion object {
        const val serialVersionUID: Long = 1L

        fun build(user: User): UserDetails {
            val authorities = user.roles.stream().map {
                SimpleGrantedAuthority(it.Name.name)
            }.collect(Collectors.toList())

            return UserDetailsImpl(
                user.id,
                user.userName,
                user.email,
                user.password,
                authorities
            )
        }
    }

    var id: Long = 0

    private var username: String
    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    var email: String

    @JsonIgnore
    private var password: String
    override fun getPassword(): String {
        return password
    }

    private var authorities: MutableCollection<out GrantedAuthority>? = null
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities!!
    }


    constructor(
        id: Long,
        username: String,
        email: String,
        password: String,
        authorities: MutableCollection<out GrantedAuthority>
    ) {
        this.id = id
        this.username = username
        this.email = email
        this.password = password
        this.authorities = authorities
    }
}