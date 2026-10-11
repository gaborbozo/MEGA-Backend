package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.resend.ResendEmailWebhookRequest
import hu.bozgab.megabackend.service.EmailService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailWebhookController(private val emailService: EmailService) {

    @PostMapping("/webhook/email")
    fun handleEmailWebhook(@RequestBody @Valid request: ResendEmailWebhookRequest): ResponseEntity<Void> =
        emailService.updateStatus(request).run { ResponseEntity.ok().build() }
}
