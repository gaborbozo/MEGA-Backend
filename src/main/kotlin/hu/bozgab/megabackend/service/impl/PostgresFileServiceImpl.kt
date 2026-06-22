package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.StoredFileDTO
import hu.bozgab.megabackend.entity.StoredFile
import hu.bozgab.megabackend.exception.EntityDeletedException
import hu.bozgab.megabackend.exception.EntityNotFoundException
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.repository.StoredFileRepository
import hu.bozgab.megabackend.service.FileService
import jakarta.transaction.Transactional
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.util.*

@Transactional
@Service
class PostgresFileServiceImpl(
    private val repository: StoredFileRepository,
    private val megaUserRepository: MegaUserRepository
) : FileService {

    override fun create(file: MultipartFile, userId: Long): UUID {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        require(!file.contentType.isNullOrBlank()) { "File content type is null" }
        require(!file.contentType!!.contains("*")) { "Wildcard content types are not allowed: ${file.contentType}" }
        val mediaType = MediaType.parseMediaType(file.contentType!!)

        return StoredFile(
            originalFileName = file.originalFilename ?: "unknown",
            contentType = mediaType.type + "/" + mediaType.subtype,
            size = file.size,
            content = file.bytes,
            createdBy = megaUser,
            deleted = false
        ).run { repository.save(this).id!! }
    }

    override fun load(uuid: UUID): StoredFileDTO =
        repository.findByIdAndDeletedIsFalse(uuid)
            .orElseThrow { FileNotFoundException() }
            .also { if (it.deleted) throw EntityDeletedException() }
            .run {
                StoredFileDTO(
                    fileName = originalFileName,
                    contentType = MediaType.parseMediaType(contentType),
                    size = size,
                    contentStream = ByteArrayResource(content ?: byteArrayOf())
                )
            }

    override fun delete(uuid: UUID) {
        repository.findByIdAndDeletedIsFalse(uuid)
            .orElseThrow { FileNotFoundException() }
            .apply { deleted = true }
            .run { repository.saveAndFlush(this) }
    }

}