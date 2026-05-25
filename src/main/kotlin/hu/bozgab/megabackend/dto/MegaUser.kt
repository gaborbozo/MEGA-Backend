package hu.bozgab.megabackend.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class MegaUser(
    val id: Long,
    username: String,
    password: String?,
    authorities: Collection<GrantedAuthority>
) : User(username, password, authorities)