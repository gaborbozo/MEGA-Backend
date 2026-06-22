package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.MegaUserDTO
import hu.bozgab.megabackend.service.GeriService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/geri")
class GeriController(private val service: GeriService) {

    @PostMapping(
        "/upload",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun upload(
        @RequestBody request: MultipartFile,
        @AuthenticationPrincipal user: MegaUserDTO
    ): ResponseEntity<Long> =
        ResponseEntity(service.upload(request, user.id), HttpStatus.CREATED)

    @GetMapping("/random")
    fun getRandom(): ResponseEntity<Resource> =
        service.getRandom().run {
            ResponseEntity.ok()
                .contentType(contentType)
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\"",
                )
                .header("X-File-Id", id.toString())
                .body(contentStream);
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }

}