package hu.bozgab.megabackend.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateShoppingItemRequest(
    @field:NotBlank
    val product: String,
)