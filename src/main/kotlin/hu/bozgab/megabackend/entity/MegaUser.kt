package hu.bozgab.megabackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "mega_user")
class MegaUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "username", unique = true)
    val username: String,

    @Column(name = "password_hash")
    val passwordHash: String,

    )