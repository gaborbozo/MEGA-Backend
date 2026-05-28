package hu.bozgab.megabackend.dto.response

data class AuthNResponse(
    val token: String,
    val expiration: Long
)