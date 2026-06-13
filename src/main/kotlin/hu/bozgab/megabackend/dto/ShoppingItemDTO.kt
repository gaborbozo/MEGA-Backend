package hu.bozgab.megabackend.dto

import java.time.Instant

data class ShoppingItemDTO(
    val id: Long,
    val product: String,
    val createdBy: String,
    val createdAt: Instant,
)
