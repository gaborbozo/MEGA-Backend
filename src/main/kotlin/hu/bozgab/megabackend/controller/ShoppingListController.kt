package hu.bozgab.megabackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shopping-list")
class ShoppingListController {

    @GetMapping
    fun getShoppingLists(): ResponseEntity<String> = ResponseEntity.ok("milk")

}