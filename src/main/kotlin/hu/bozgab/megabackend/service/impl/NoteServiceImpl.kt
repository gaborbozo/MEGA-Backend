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

    override fun create(userId: Long, request: CreateNoteRequest): NoteDTO {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { NotFoundException() }

        val note = Note(
            note = request.note,
            color = request.color,
            createdBy = megaUser,
            updatedBy = megaUser
        )

        return mapToDTO(noteRepository.saveAndFlush(note))
    }

    override fun getById(id: Long): NoteDTO =
        noteRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { NotFoundException() }
            .let { mapToDTO(it) }

    override fun getAll(): List<NoteDTO> =
        noteRepository.findAllByDeletedIsFalse()
            .map { mapToDTO(it) }

    override fun update(userId: Long, id: Long, request: UpdateNoteRequest): NoteDTO {
        val existingNote = noteRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { NotFoundException() }
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { NotFoundException() }

        request.note?.let { existingNote.note = it }
        request.color?.let { existingNote.color = it }
        existingNote.updatedBy = megaUser

        return mapToDTO(noteRepository.saveAndFlush(existingNote))
    }

    override fun delete(userId: Long, id: Long) {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { NotFoundException() }

        noteRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { NotFoundException() }
            .apply {
                deleted = true
                updatedBy = megaUser
            }
            .run { noteRepository.saveAndFlush(this) }
    }

    private fun mapToDTO(note: Note): NoteDTO = NoteDTO(
        id = note.id!!,
        note = note.note,
        color = note.color,
        createdBy = note.createdBy.username,
        createdAt = note.createdAt,
        updatedBy = note.updatedBy.username,
        updatedAt = note.updatedAt,
    )

}
