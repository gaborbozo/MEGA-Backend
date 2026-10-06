package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.EmailDto
import hu.bozgab.megabackend.entity.Email

interface EmailService {
    fun send(email: EmailDto): Email
}
