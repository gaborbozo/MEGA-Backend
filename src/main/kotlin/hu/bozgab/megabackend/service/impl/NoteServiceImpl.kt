package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.NoteDTO
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest
import hu.bozgab.megabackend.entity.Note
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.repository.NoteRepository
import hu.bozgab.megabackend.service.NoteService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val megaUserRepository: MegaUserRepository
) : NoteService {

    override fun createNote(userId: Long, request: CreateNoteRequest): NoteDTO {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { NotFoundException() }

        val note = Note(
            note = request.note,
            createdBy = megaUser,
        )

        return mapToDTO(noteRepository.save(note))
    }

    override fun getNoteById(id: Long): NoteDTO =
        noteRepository.findById(id)
            .orElseThrow { NotFoundException() }
            .let { mapToDTO(it) }

    override fun getAllNotes(): List<NoteDTO> =
        noteRepository.findAll()
            .map { mapToDTO(it) }

    override fun updateNote(id: Long, request: UpdateNoteRequest): NoteDTO {
        val existingNote = noteRepository.findById(id)
            .orElseThrow { NotFoundException() }

        request.note?.let { existingNote.note = it }

        return mapToDTO(noteRepository.save(existingNote))
    }

    override fun deleteNote(id: Long) {
        noteRepository.deleteById(id)
    }

    private fun mapToDTO(note: Note): NoteDTO {
        return NoteDTO(
            id = note.id!!,
            note = note.note,
            createdBy = note.createdBy.username,
            createdAt = note.createdAt
        )
    }
}
