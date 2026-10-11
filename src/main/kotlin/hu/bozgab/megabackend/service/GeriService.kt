package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.StoredFileDto
import org.springframework.web.multipart.MultipartFile

interface GeriService {
    fun upload(file: MultipartFile, userId: Long): Long
    fun getRandom(): StoredFileDto
    fun delete(id: Long)
}