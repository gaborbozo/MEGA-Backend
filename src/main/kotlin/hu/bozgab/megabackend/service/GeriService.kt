package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.StoredFileDTO
import org.springframework.web.multipart.MultipartFile

interface GeriService {
    fun upload(file: MultipartFile, userId: Long): Long
    fun getRandom(): StoredFileDTO
    fun delete(id: Long)
}