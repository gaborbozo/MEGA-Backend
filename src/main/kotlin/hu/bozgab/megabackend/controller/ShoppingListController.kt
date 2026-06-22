package hu.bozgab.megabackend.controller

import hu.bozgab.megabackend.dto.MegaUserDTO
import hu.bozgab.megabackend.dto.ShoppingItemDTO
import hu.bozgab.megabackend.dto.request.CreateShoppingItemRequest
import hu.bozgab.megabackend.service.ShoppingItemService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shopping-list")
class ShoppingListController(
    private val service: ShoppingItemService
) {

    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateShoppingItemRequest,
        @AuthenticationPrincipal user: MegaUserDTO
    ): ResponseEntity<ShoppingItemDTO> =
        ResponseEntity(service.create(user.id, request), HttpStatus.CREATED)

    @GetMapping
    fun getByYearAndWeek(
        @RequestParam year: Int,
        @RequestParam week: Int
    ): ResponseEntity<List<ShoppingItemDTO>> =
        ResponseEntity(service.getByYearAndWeek(year, week), HttpStatus.OK)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }

}