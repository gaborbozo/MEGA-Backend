package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.MegaUserDTO
import hu.bozgab.megabackend.dto.request.UpdateUserRequest
import hu.bozgab.megabackend.dto.request.UpdateUserResponse
import hu.bozgab.megabackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @PatchMapping()
    fun updateUser(
        @RequestBody request: UpdateUserRequest,
        @AuthenticationPrincipal user: MegaUserDTO
    ): ResponseEntity<UpdateUserResponse> =
        ResponseEntity(userService.updateUser(user.id, request), HttpStatus.OK)

}