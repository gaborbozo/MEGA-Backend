package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailRepository : JpaRepository<Email, Long> {
    fun findByResendId(resendId: String): Email?
}
