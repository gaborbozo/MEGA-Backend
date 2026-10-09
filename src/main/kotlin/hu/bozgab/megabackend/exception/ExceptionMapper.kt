package hu.bozgab.megabackend.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionMapper {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(): ResponseEntity<Void> = ResponseEntity.notFound().build()
}
