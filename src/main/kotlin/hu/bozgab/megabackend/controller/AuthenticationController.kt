package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.request.AuthNResponse
import hu.bozgab.megabackend.dto.request.LoginRequest
import hu.bozgab.megabackend.service.authentication.AuthenticationService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest
    ): ResponseEntity<AuthNResponse> = ResponseEntity.ok(authenticationService.login(request))

}