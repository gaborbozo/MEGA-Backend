package hu.bozgab.megabackend.entity

import hu.bozgab.megabackend.entity.enum.EmailStatus
import jakarta.persistence.*

@Entity
@Table(name = "email")
class Email(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "subject", nullable = false, length = 998)
    var subject: String,

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    var message: String,

    @Column(name = "send_from", nullable = false, length = 320)
    var sendFrom: String,

    @Column(name = "send_to", nullable = false, length = 320)
    var sendTo: String,

    @Column(name = "resend_id", unique = true)
    var resendId: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    var status: EmailStatus = EmailStatus.READY_TO_SEND
)
