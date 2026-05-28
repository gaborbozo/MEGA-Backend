package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long>
