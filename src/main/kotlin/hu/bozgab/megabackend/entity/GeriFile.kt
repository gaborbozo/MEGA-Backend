package hu.bozgab.megabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "geri_file")
class GeriFile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stored_file_id", nullable = false)
    var storedFile: StoredFile,

    @Column(name = "file_name", nullable = false)
    var fileName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    var createdBy: MegaUser,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = Instant.now(),

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false

)