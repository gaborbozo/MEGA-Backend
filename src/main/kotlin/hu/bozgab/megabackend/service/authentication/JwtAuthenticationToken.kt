package hu.bozgab.megabackend.service.authentication

import hu.bozgab.megabackend.dto.MegaUserDTO
import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken : AbstractAuthenticationToken {

    private val token: String?
    private val principal: MegaUserDTO?

    // non-authenticated
    constructor(token: String) : super(null) {
        this.token = token
        this.principal = null
        super.setAuthenticated(false)
    }

    // authenticated
    constructor(principal: MegaUserDTO) : super(principal.authorities) {
        this.token = null
        this.principal = principal
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any? = token

    override fun getPrincipal(): MegaUserDTO? = principal

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JwtAuthenticationToken) return false

        return token == other.token && super.equals(other)
    }

    override fun hashCode(): Int = super.hashCode() xor token.hashCode()

}