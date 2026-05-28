package hu.bozgab.megabackend.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateNoteRequest(
    @field:NotBlank
    val note: String,
)
