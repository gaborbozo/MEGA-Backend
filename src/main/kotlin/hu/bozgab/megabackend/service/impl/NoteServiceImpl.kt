package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.NoteDto
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

    override fun create(userId: Long, request: CreateNoteRequest): NoteDto {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        val note = Note(
            note = request.note,
            color = request.color,
            createdBy = megaUser,
            updatedBy = megaUser
        )

        return mapToDto(repository.saveAndFlush(note))
    }

    override fun getById(id: Long): NoteDto =
        repository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { EntityNotFoundException() }
            .let { mapToDto(it) }

    override fun getAll(): List<NoteDto> =
        repository.findAllByDeletedIsFalse()
            .map { mapToDto(it) }

    override fun update(userId: Long, id: Long, request: UpdateNoteRequest): NoteDto {
        val existingNote = repository.findByIdAndDeletedIsFalse(id)
            .orElseThrow { EntityNotFoundException() }
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        request.note?.let { existingNote.note = it }
        request.color?.let { existingNote.color = it }
        existingNote.updatedBy = megaUser

        return mapToDto(repository.saveAndFlush(existingNote))
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

    private fun mapToDto(note: Note): NoteDto = NoteDto(
        id = note.id!!,
        note = note.note,
        color = note.color,
        createdBy = note.createdBy.username,
        createdAt = note.createdAt,
        updatedBy = note.updatedBy.username,
        updatedAt = note.updatedAt,
    )

}
