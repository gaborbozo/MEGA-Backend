package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.ShoppingItemDTO
import hu.bozgab.megabackend.dto.request.CreateShoppingItemRequest

interface ShoppingItemService {
    fun create(userId: Long, request: CreateShoppingItemRequest): ShoppingItemDTO
    fun delete(id: Long)
    fun getByYearAndWeek(year: Int, week: Int): List<ShoppingItemDTO>
}
