package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.NoteDTO
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest

interface NoteService {
    fun create(userId: Long, request: CreateNoteRequest): NoteDTO
    fun getById(id: Long): NoteDTO
    fun getAll(): List<NoteDTO>
    fun update(userId: Long, id: Long, request: UpdateNoteRequest): NoteDTO
    fun delete(userId: Long, id: Long)
}
