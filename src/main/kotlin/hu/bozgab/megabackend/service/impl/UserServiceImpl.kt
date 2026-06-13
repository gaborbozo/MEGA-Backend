package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.request.UpdateUserRequest
import hu.bozgab.megabackend.dto.request.UpdateUserResponse
import hu.bozgab.megabackend.entity.MegaUser
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.service.UserService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserServiceImpl(
    private val megaUserRepository: MegaUserRepository
) : UserService {

    override fun update(
        userId: Long,
        request: UpdateUserRequest
    ): UpdateUserResponse {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { NotFoundException() }

        request.theme?.let { megaUser.theme = it }

        return mapToUpdateResponse(megaUserRepository.saveAndFlush(megaUser))
    }

    private fun mapToUpdateResponse(megaUser: MegaUser): UpdateUserResponse {
        return UpdateUserResponse(
            theme = megaUser.theme
        )
    }

}
