package hu.bozgab.megabackend.service.authentication

import hu.bozgab.megabackend.dto.MegaUserDTO
import hu.bozgab.megabackend.dto.request.AuthNResponse
import hu.bozgab.megabackend.dto.request.LoginRequest
import hu.bozgab.megabackend.exception.JwtAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(loginRequest: LoginRequest): AuthNResponse {
        val userDetails = userDetailsService.loadUserByUsername(loginRequest.username)
        return (userDetails as MegaUserDTO).let { user ->
            if (!passwordEncoder.matches(loginRequest.password, user.password))
                throw JwtAuthenticationException(HttpStatus.UNAUTHORIZED.name)

            AuthNResponse(
                userId = user.id,
                expiration = jwtService.tokenExpiration,
                token = jwtService.generateToken(user),
                theme = user.theme,
            )
        }
    }

}