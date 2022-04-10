package org.study.sharedrive.model

import javax.persistence.*

@Entity
@Table(name="troles")
data class Role  (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val ID : Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    val Name: ERole
)