package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.MegaUserDTO
import hu.bozgab.megabackend.dto.NoteDTO
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest
import hu.bozgab.megabackend.service.NoteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/note")
class NoteController(private val noteService: NoteService) {

    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateNoteRequest,
        @AuthenticationPrincipal user: MegaUserDTO
    ): ResponseEntity<NoteDTO> =
        ResponseEntity(noteService.create(user.id, request), HttpStatus.CREATED)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<NoteDTO> =
        ResponseEntity(noteService.getById(id), HttpStatus.OK)


    @GetMapping
    fun getAll(): ResponseEntity<List<NoteDTO>> =
        ResponseEntity(noteService.getAll(), HttpStatus.OK)

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateNoteRequest,
        @AuthenticationPrincipal user: MegaUserDTO
    ): ResponseEntity<NoteDTO> =
        ResponseEntity(noteService.update(user.id, id, request), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: MegaUserDTO
    ): ResponseEntity<Void> {
        noteService.delete(user.id, id)
        return ResponseEntity(HttpStatus.OK)
    }
    
}
