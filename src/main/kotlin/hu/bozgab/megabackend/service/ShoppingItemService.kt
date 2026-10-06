package hu.bozgab.megabackend.service

import hu.bozgab.megabackend.dto.ShoppingItemDto
import hu.bozgab.megabackend.dto.request.CreateShoppingItemRequest

interface ShoppingItemService {
    fun create(userId: Long, request: CreateShoppingItemRequest): ShoppingItemDto
    fun delete(id: Long)
    fun getByYearAndWeek(year: Int, week: Int): List<ShoppingItemDto>
}
