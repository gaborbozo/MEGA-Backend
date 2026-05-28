package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.request.LoginRequest
import hu.bozgab.megabackend.dto.response.AuthNResponse
import hu.bozgab.megabackend.service.authentication.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest
    ): ResponseEntity<AuthNResponse> = ResponseEntity.ok(userService.login(request))
    
}