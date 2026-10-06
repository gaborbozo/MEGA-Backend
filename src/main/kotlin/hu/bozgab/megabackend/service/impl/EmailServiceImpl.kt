package hu.bozgab.megabackend.service.impl

import com.resend.Resend
import com.resend.services.emails.model.Attachment
import com.resend.services.emails.model.CreateEmailOptions
import hu.bozgab.megabackend.dto.EmailDto
import hu.bozgab.megabackend.entity.Email
import hu.bozgab.megabackend.entity.enum.EmailStatus
import hu.bozgab.megabackend.repository.EmailRepository
import hu.bozgab.megabackend.service.EmailService
import hu.bozgab.megabackend.service.GeriService
import hu.bozgab.megabackend.service.util.HtmlUtil.Companion.toHtml
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmailServiceImpl(
    private val geriService: GeriService,
    private val emailRepository: EmailRepository,
    @Value($$"${app.email.user.info.address}") private val sendFrom: String,
    @Value($$"${app.email.resend.api.key}") private val apiKey: String
) : EmailService {

    override fun send(email: EmailDto): Email {
        require(apiKey.isNotBlank()) { "EMAIL_RESEND_API_KEY must be configured to send email" }
        require(sendFrom.isNotBlank()) { "EMAIL_USER_INFO_ADDRESS must be configured to send email" }

        val html = ClassPathResource(GENERIC_TEMPLATE_PATH).inputStream.bufferedReader().use { it.readText() }
            .replace(GENERIC_TEMPLATE_MESSAGE_PLACEHOLDER, email.message.toHtml())

        val options = CreateEmailOptions.builder()
            .from(sendFrom)
            .to(email.sendTo)
            .subject(email.subject)
            .html(html)
            .addAttachment(buildRandomGeriAttachment())
            .build()

        val response = Resend(apiKey).emails().send(options)

        return emailRepository.saveAndFlush(
            Email(
                subject = email.subject,
                message = email.message,
                sendFrom = sendFrom,
                sendTo = email.sendTo,
                status = EmailStatus.WAITING_FOR_RESEND,
                resendId = response.id
            )
        )
    }

    private fun buildRandomGeriAttachment(): Attachment {
        val photo = geriService.getRandom()
        val photoBytes = photo.contentStream.inputStream.use { it.readBytes() }
        return Attachment.builder()
            .fileName(photo.fileName)
            .content(Base64.getEncoder().encodeToString(photoBytes))
            .contentType(photo.contentType.toString())
            .contentId(GERI_PHOTO_CONTENT_ID)
            .build()
    }

    private companion object {
        const val GENERIC_TEMPLATE_PATH = "static/email/generic_template.html"
        const val GENERIC_TEMPLATE_MESSAGE_PLACEHOLDER = "{{message}}"
        const val GERI_PHOTO_CONTENT_ID = "geri-photo"
    }
}
