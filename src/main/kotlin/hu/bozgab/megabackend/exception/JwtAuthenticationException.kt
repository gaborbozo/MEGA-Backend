package hu.bozgab.megabackend.exception

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(message: String) : AuthenticationException(message)