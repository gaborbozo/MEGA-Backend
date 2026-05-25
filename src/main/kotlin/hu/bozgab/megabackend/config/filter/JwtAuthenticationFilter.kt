package hu.bozgab.megabackend.config.filter

import hu.bozgab.megabackend.service.authentication.JwtAuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager
) : OncePerRequestFilter() {

    companion object {
        private const val BEARER = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (header == null || !header.startsWith(BEARER)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.substring(BEARER.length)
        val authRequest = JwtAuthenticationToken(token)

        val authResult: Authentication =
            authenticationManager.authenticate(authRequest)
        SecurityContextHolder.getContext()
            .setAuthentication(authResult)

        filterChain.doFilter(request, response)
    }
}