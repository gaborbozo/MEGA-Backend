package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.EmailDto
import hu.bozgab.megabackend.dto.resend.ResendEmailWebhookRequest
import hu.bozgab.megabackend.entity.Email

interface EmailService {
    fun send(email: EmailDto): Email
    fun updateStatus(request: ResendEmailWebhookRequest)
}
