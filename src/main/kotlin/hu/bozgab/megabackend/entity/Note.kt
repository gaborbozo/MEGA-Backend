package hu.bozgab.megabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Table(name = "note")
@Entity
class Note(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "note", nullable = false)
    var note: String,

    @Column(name = "color")
    var color: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    var createdBy: MegaUser,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    var updatedBy: MegaUser,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false,
)