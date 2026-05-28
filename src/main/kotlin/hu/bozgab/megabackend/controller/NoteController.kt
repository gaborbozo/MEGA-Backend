package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.MegaUser
import hu.bozgab.megabackend.dto.NoteDTO
import hu.bozgab.megabackend.dto.request.CreateNoteRequest
import hu.bozgab.megabackend.dto.request.UpdateNoteRequest
import hu.bozgab.megabackend.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/note")
class NoteController(private val noteService: NoteService) {

    @PostMapping
    fun createNote(
        @RequestBody request: CreateNoteRequest,
        @AuthenticationPrincipal user: MegaUser
    ): ResponseEntity<NoteDTO> =
        ResponseEntity(noteService.createNote(user.id, request), HttpStatus.CREATED)

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Long): ResponseEntity<NoteDTO> =
        ResponseEntity(noteService.getNoteById(id), HttpStatus.OK)


    @GetMapping
    fun getAllNotes(): ResponseEntity<List<NoteDTO>> =
        ResponseEntity(noteService.getAllNotes(), HttpStatus.OK)

    @PatchMapping("/{id}")
    fun updateNote(@PathVariable id: Long, @RequestBody request: UpdateNoteRequest): ResponseEntity<NoteDTO> =
        ResponseEntity(noteService.updateNote(id, request), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Void> {
        noteService.deleteNote(id)
        return ResponseEntity(HttpStatus.OK)
    }
    
}
