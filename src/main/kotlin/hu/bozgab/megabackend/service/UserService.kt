package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.request.UpdateUserRequest
import hu.bozgab.megabackend.dto.request.UpdateUserResponse

interface UserService {
    fun update(userId: Long, request: UpdateUserRequest): UpdateUserResponse
}
