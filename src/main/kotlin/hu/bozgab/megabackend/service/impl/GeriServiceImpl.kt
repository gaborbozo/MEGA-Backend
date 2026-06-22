package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.StoredFileDTO
import hu.bozgab.megabackend.entity.GeriFile
import hu.bozgab.megabackend.exception.EntityNotFoundException
import hu.bozgab.megabackend.repository.GeriFileRepository
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.repository.StoredFileRepository
import hu.bozgab.megabackend.service.FileService
import hu.bozgab.megabackend.service.GeriService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional
@Service
class GeriServiceImpl(
    private val fileService: FileService,
    private val repository: GeriFileRepository,
    private val megaUserRepository: MegaUserRepository,
    private val fileRepository: StoredFileRepository
) : GeriService {

    override fun upload(file: MultipartFile, userId: Long): Long {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        return fileRepository.findById(fileService.create(file, userId)).get()
            .let {
                GeriFile(
                    fileName = it.originalFileName,
                    storedFile = it,
                    createdBy = megaUser
                )
            }.run { repository.save(this).id!! }
    }

    override fun getRandom(): StoredFileDTO =
        repository.findRandomAndDeletedIsFalse()
            .orElseThrow { EntityNotFoundException() }
            .let { geriFile ->
                fileService.load(geriFile.storedFile.id!!).apply {
                    id = geriFile.id
                    fileName = fileName
                }
            }

    override fun delete(id: Long) {
        repository.findById(id)
            .orElseThrow { EntityNotFoundException() }
            .also { fileService.delete(it.storedFile.id!!) }
            .apply { deleted = true }
            .run { repository.saveAndFlush(this) }
    }

}