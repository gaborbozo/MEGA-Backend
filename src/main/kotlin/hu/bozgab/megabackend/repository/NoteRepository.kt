package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NoteRepository : JpaRepository<Note, Long> {

    fun findAllByDeletedIsFalse(): List<Note>

    fun findByIdAndDeletedIsFalse(id: Long): Optional<Note>

}
