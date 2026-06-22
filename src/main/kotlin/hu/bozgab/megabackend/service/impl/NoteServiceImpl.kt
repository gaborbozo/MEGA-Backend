package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.NoteDTO
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest
import hu.bozgab.megabackend.entity.Note
import hu.bozgab.megabackend.exception.EntityNotFoundException
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.repository.NoteRepository
import hu.bozgab.megabackend.service.NoteService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class NoteServiceImpl(
    private val repository: NoteRepository,
    private val megaUserRepository: MegaUserRepository
) : NoteService {

    override fun create(userId: Long, request: CreateNoteRequest): NoteDTO {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        val note = Note(
            note = request.note,
            color = request.color,
            createdBy = megaUser,
            updatedBy = megaUser
        )

        return mapToDTO(repository.saveAndFlush(note))
    }

    override fun getById(id: Long): NoteDTO =
        repository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { EntityNotFoundException() }
            .let { mapToDTO(it) }

    override fun getAll(): List<NoteDTO> =
        repository.findAllByDeletedIsFalse()
            .map { mapToDTO(it) }

    override fun update(userId: Long, id: Long, request: UpdateNoteRequest): NoteDTO {
        val existingNote = repository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { EntityNotFoundException() }
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        request.note?.let { existingNote.note = it }
        request.color?.let { existingNote.color = it }
        existingNote.updatedBy = megaUser

        return mapToDTO(repository.saveAndFlush(existingNote))
    }

    override fun delete(userId: Long, id: Long) {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        repository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { EntityNotFoundException() }
            .apply {
                deleted = true
                updatedBy = megaUser
            }
            .run { repository.saveAndFlush(this) }
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
