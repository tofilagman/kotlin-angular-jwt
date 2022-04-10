package org.study.sharedrive.model

import javax.persistence.*

@Entity
@Table(name = "tuser")
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val ID: Long,

        @NotBlank
        @Column(name = "user_name")
        @Size(max =20)
        val UserName: String,

        @Column(name = "password")
        val Password: String,

        @Column(name = "email")
        val Email: String,

        @Column(name = "uid")
        val UID: String,

        @Column(name = "status_type")
        val StatusType: Int,

        @Column(name = "public_key")
        val PublicKey: String,

        @Column(name = "private_key")
        val PrivateKey: String,

        @Column(name = "token")
        val Token : String,

        @Column(name = "contact_no")
        val ContactNo: String,

        @Column(name = "id_user_created")
        val ID_UserCreated: Long,

        @Column(name = "udf1")
        val UDF1: String?,

        @Column(name = "udf2")
        val UDF2: String?,

        @Column(name = "udf3")
        val UDF3: String?
)