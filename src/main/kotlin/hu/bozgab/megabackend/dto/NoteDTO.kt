package hu.bozgab.megabackend.dto

import java.time.Instant

data class NoteDTO(
    val id: Long,
    val note: String,
    val color: String? = null,
    val createdBy: String,
    val createdAt: Instant,
    val updatedBy: String,
    val updatedAt: Instant
)
