package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.MegaUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MegaUserRepository : JpaRepository<MegaUser, Long> {

    fun findByUsername(username: String): Optional<MegaUser>

}