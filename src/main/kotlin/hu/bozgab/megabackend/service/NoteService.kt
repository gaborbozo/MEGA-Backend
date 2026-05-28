package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.NoteDTO
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest

interface NoteService {
    fun createNote(userId: Long, request: CreateNoteRequest): NoteDTO
    fun getNoteById(id: Long): NoteDTO
    fun getAllNotes(): List<NoteDTO>
    fun updateNote(id: Long, request: UpdateNoteRequest): NoteDTO
    fun deleteNote(id: Long)
}
