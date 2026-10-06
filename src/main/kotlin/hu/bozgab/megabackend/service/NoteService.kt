package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.NoteDto
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest

interface NoteService {
    fun create(userId: Long, request: CreateNoteRequest): NoteDto
    fun getById(id: Long): NoteDto
    fun getAll(): List<NoteDto>
    fun update(userId: Long, id: Long, request: UpdateNoteRequest): NoteDto
    fun delete(userId: Long, id: Long)
}
