package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.EmailDto
import hu.bozgab.megabackend.entity.Email
import hu.bozgab.megabackend.service.EmailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/email")
class EmailController(private val emailService: EmailService) {

    @PostMapping("/test")
    fun sendTestEmail(@RequestBody email: EmailDto): ResponseEntity<Email> =
        ResponseEntity(emailService.send(email), HttpStatus.CREATED)
}
