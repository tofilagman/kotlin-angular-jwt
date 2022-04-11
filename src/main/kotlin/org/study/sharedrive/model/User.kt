package org.study.sharedrive.model

import javax.persistence.*

@Entity
@Table(name = "tuser")
class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long = 0

        @Column(name = "user_name")
        var userName: String = ""

        @Column(name = "password")
        var password: String = ""

        @Column(name = "email")
        var email: String = ""

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "tuser_roles",
                joinColumns = [JoinColumn(name = "id_user")],
                inverseJoinColumns = [JoinColumn(name = "id_role")]
        )
        var roles: Set<Role> = emptySet()

        constructor(
                userName: String,
                password: String,
                email: String
        ) {
                this.userName = userName
                this.password = password
                this.email = email
        }
}