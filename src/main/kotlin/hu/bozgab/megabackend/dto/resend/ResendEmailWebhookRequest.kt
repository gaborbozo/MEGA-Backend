package hu.bozgab.megabackend.dto.resend

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class ResendEmailWebhookRequest(
    @JsonProperty("created_at")
    val createdAt: String? = null,
    @field:NotNull
    @field:Valid
    val data: Data? = null,
    @field:NotNull
    @field:Pattern(
        regexp = "email\\.(bounced|clicked|complained|delivered|delivery_delayed|failed|opened|received|scheduled|sent|suppressed)"
    )
    val type: String? = null
) {
    data class Data(
        @JsonProperty("created_at")
        val createdAt: String? = null,
        @field:NotNull
        @JsonProperty("email_id")
        val emailId: String? = null,
        @JsonProperty("from")
        val from: String? = null,
        @JsonProperty("message_id")
        val messageId: String? = null,
        val subject: String? = null,
        @JsonProperty("to")
        val recipients: List<String>? = null
    )

    fun emailId(): String? = data?.emailId
}
