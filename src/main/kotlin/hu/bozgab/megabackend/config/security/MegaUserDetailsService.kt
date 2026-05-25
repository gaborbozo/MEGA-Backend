package hu.bozgab.megabackend.config.security

import hu.bozgab.megabackend.dto.MegaUser
import hu.bozgab.megabackend.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MegaUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found: $username") }

        return MegaUser(
            user.id,
            user.username,
            user.passwordHash,
            emptyList()
        )
    }
}