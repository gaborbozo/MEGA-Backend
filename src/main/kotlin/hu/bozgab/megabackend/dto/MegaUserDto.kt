package hu.bozgab.megabackend.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class MegaUserDto(
    val id: Long,
    val theme: String?,
    username: String,
    password: String?,
    authorities: Collection<GrantedAuthority>
) : User(username, password, authorities)