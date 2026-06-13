package hu.bozgab.megabackend.entity

import jakarta.persistence.*

@Table(name = "mega_user")
@Entity
class MegaUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0,

    @Column(name = "username", unique = true)
    var username: String,

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String,

    @Column(name = "theme")
    var theme: String,

    )