package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.StoredFileDto
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface FileService {
    fun create(file: MultipartFile, userId: Long): UUID
    fun load(uuid: UUID): StoredFileDto
    fun delete(uuid: UUID)
}