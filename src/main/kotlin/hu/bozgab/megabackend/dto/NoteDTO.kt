package hu.bozgab.megabackend.dto

import java.time.Instant

data class NoteDTO(
    val id: Long,
    val note: String,
    val createdBy: String,
    val createdAt: Instant
)
