package hu.bozgab.megabackend.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration(
    @Value("\${app.client.http.addresses}") private val clientHttpAddress: List<String>
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(*clientHttpAddress.toTypedArray())
            .allowedMethods(*HttpMethod.values().map { it.name() }.toTypedArray())
            .allowedHeaders("*")
            .exposedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)
    }
}