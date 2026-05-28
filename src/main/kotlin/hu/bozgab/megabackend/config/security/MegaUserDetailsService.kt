package hu.bozgab.megabackend.config.security

import hu.bozgab.megabackend.dto.MegaUser
import hu.bozgab.megabackend.repository.MegaUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MegaUserDetailsService(
    private val megaUserRepository: MegaUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = megaUserRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found: $username") }

        return MegaUser(
            user.id,
            user.username,
            user.passwordHash,
            emptyList()
        )
    }
}