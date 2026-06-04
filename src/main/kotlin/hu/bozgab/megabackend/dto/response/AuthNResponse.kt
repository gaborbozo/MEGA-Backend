package hu.bozgab.megabackend.dto.response

data class AuthNResponse(
    val userId: Long,
    val token: String,
    val expiration: Long
)