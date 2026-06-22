package hu.bozgab.megabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.*

@Entity
@Table(name = "stored_file")
class StoredFile(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null,

    @Column(name = "original_file_name", nullable = false)
    var originalFileName: String,

    @Column(name = "content_type", nullable = false)
    var contentType: String,

    @Column(name = "size", nullable = false)
    var size: Long,

    @JdbcTypeCode(SqlTypes.BINARY)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content")
    var content: ByteArray? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    var createdBy: MegaUser,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false

)