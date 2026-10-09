package hu.bozgab.megabackend.entity.enum

enum class EmailStatus {
    READY_TO_SEND,
    WAITING_FOR_RESEND,

    BOUNCED,
    CLICKED,
    COMPLAINED,
    DELIVERED,
    DELIVERY_DELAYED,
    FAILED,
    OPENED,
    RECEIVED,
    SCHEDULED,
    SENT,
    SUPPRESSED;

    fun canTransitionTo(nextStatus: EmailStatus): Boolean =
        nextStatus == this || when (this) {
            READY_TO_SEND, WAITING_FOR_RESEND -> true
            SCHEDULED -> nextStatus in setOf(
                SENT, DELIVERY_DELAYED, DELIVERED, BOUNCED, FAILED, OPENED, CLICKED, COMPLAINED, SUPPRESSED
            )
            SENT -> nextStatus in setOf(
                DELIVERY_DELAYED, DELIVERED, BOUNCED, FAILED, OPENED, CLICKED, COMPLAINED, SUPPRESSED
            )
            DELIVERY_DELAYED -> nextStatus in setOf(
                DELIVERED, BOUNCED, FAILED, OPENED, CLICKED, COMPLAINED, SUPPRESSED
            )
            DELIVERED -> nextStatus in setOf(OPENED, CLICKED, COMPLAINED)
            OPENED -> nextStatus in setOf(CLICKED, COMPLAINED)
            CLICKED -> nextStatus == COMPLAINED
            BOUNCED, COMPLAINED, FAILED, RECEIVED, SUPPRESSED -> false
        }

    companion object {
        fun fromResendEventType(type: String?): EmailStatus? = when (type) {
            "email.bounced" -> BOUNCED
            "email.clicked" -> CLICKED
            "email.complained" -> COMPLAINED
            "email.delivered" -> DELIVERED
            "email.delivery_delayed" -> DELIVERY_DELAYED
            "email.failed" -> FAILED
            "email.opened" -> OPENED
            "email.received" -> RECEIVED
            "email.scheduled" -> SCHEDULED
            "email.sent" -> SENT
            "email.suppressed" -> SUPPRESSED
            else -> null
        }
    }
}