package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.request.UpdateUserRequest
import hu.bozgab.megabackend.dto.request.UpdateUserResponse

interface UserService {
    fun updateUser(userId: Long, request: UpdateUserRequest): UpdateUserResponse
}
