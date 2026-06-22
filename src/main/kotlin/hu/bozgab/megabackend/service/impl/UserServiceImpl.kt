package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.request.UpdateUserRequest
import hu.bozgab.megabackend.dto.request.UpdateUserResponse
import hu.bozgab.megabackend.entity.MegaUser
import hu.bozgab.megabackend.exception.EntityNotFoundException
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserServiceImpl(
    private val repository: MegaUserRepository
) : UserService {

    override fun update(
        userId: Long,
        request: UpdateUserRequest
    ): UpdateUserResponse {
        val megaUser = repository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        request.theme?.let { megaUser.theme = it }

        return mapToUpdateResponse(repository.saveAndFlush(megaUser))
    }

    private fun mapToUpdateResponse(megaUser: MegaUser): UpdateUserResponse {
        return UpdateUserResponse(
            theme = megaUser.theme
        )
    }

}
