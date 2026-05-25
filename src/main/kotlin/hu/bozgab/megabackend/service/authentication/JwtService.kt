package hu.bozgab.megabackend.service.authentication

import hu.bozgab.megabackend.dto.MegaUser
import hu.bozgab.megabackend.exception.JwtAuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${app.jwt.secret-key}") val secretKey: String,
    @Value("\${app.jwt.expiration-time}") val tokenExpiration: Long
) {

    companion object {
        const val KEY_ID = "ID"
    }

    fun extractUsername(token: String): String =
        extractClaim(token) { it.subject }

    fun extractId(token: String): Long =
        extractClaim(token) { claims ->
            claims[KEY_ID].toString().toLong()
        }

    fun generateToken(user: MegaUser): String =
        generateToken(mutableMapOf(), user)

    fun isTokenValid(jwt: String): Boolean =
        !isTokenExpired(jwt)

    private fun isTokenExpired(jwt: String): Boolean =
        extractClaim(jwt) { it.expiration }.before(Date())

    private fun generateToken(
        extraClaims: MutableMap<String, Any>,
        user: MegaUser
    ): String {
        val now = System.currentTimeMillis()

        extraClaims[KEY_ID] = user.id

        return Jwts.builder()
            .claims(extraClaims)
            .subject(user.username)
            .issuedAt(Date(now))
            .expiration(Date(now + tokenExpiration * 1000))
            .signWith(signingKey(), Jwts.SIG.HS256)
            .compact()
    }

    private fun <T> extractClaim(jwt: String, resolver: (Claims) -> T): T {
        val claims = extractAllClaims(jwt)
        return resolver(claims)
    }

    private fun extractAllClaims(jwt: String): Claims {
        return try {
            Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(jwt)
                .payload
        } catch (e: JwtException) {
            throw JwtAuthenticationException(e.message ?: "Invalid JWT")
        }
    }

    private fun signingKey(): SecretKey {
        val bytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(bytes)
    }
}