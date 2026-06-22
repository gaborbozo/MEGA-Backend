package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.StoredFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoredFileRepository : JpaRepository<StoredFile, UUID> {

    fun findByIdAndDeletedIsFalse(id: UUID): Optional<StoredFile>

}