package hu.bozgab.megabackend.config.security

import hu.bozgab.megabackend.dto.MegaUserDTO
import hu.bozgab.megabackend.exception.JwtAuthenticationException
import hu.bozgab.megabackend.service.authentication.JwtAuthenticationToken
import hu.bozgab.megabackend.service.authentication.JwtService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider(
    private val jwtService: JwtService
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication.credentials as String

        if (!jwtService.isTokenValid(token)) {
            throw JwtAuthenticationException("Invalid JWT token")
        }

        return token.let {
            JwtAuthenticationToken(
                principal = MegaUserDTO(
                    id = jwtService.extractId(it),
                    theme = null,
                    username = jwtService.extractUsername(it),
                    password = "",
                    authorities = emptyList()
                )
            )
        }
    }

    override fun supports(authentication: Class<*>): Boolean =
        JwtAuthenticationToken::class.java.isAssignableFrom(authentication)

}