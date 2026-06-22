package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.StoredFileDTO
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface FileService {
    fun create(file: MultipartFile, userId: Long): UUID
    fun load(uuid: UUID): StoredFileDTO
    fun delete(uuid: UUID)
}