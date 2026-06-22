package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.GeriFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GeriFileRepository : JpaRepository<GeriFile, Long> {

    @Query(value = "SELECT * FROM geri_file WHERE deleted = FALSE ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    fun findRandomAndDeletedIsFalse(): Optional<GeriFile>

}