package hu.bozgab.megabackend.dto

import org.springframework.core.io.Resource
import org.springframework.http.MediaType

data class StoredFileDTO(
    // Service specific value
    var id: Long? = null,

    var fileName: String,
    val contentType: MediaType,
    val size: Long,
    val contentStream: Resource
)