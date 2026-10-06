package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.ShoppingItemDto
import hu.bozgab.megabackend.dto.request.CreateShoppingItemRequest
import hu.bozgab.megabackend.entity.ShoppingItem
import hu.bozgab.megabackend.exception.EntityNotFoundException
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.repository.ShoppingItemRepository
import hu.bozgab.megabackend.service.ShoppingItemService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ShoppingItemServiceImpl(
    private val repository: ShoppingItemRepository,
    private val megaUserRepository: MegaUserRepository
) : ShoppingItemService {

    override fun create(userId: Long, request: CreateShoppingItemRequest): ShoppingItemDto {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { EntityNotFoundException() }

        val shoppingItem = ShoppingItem(
            product = request.product,
            createdBy = megaUser
        )

        return mapToDto(repository.saveAndFlush(shoppingItem))
    }

    override fun getByYearAndWeek(year: Int, week: Int): List<ShoppingItemDto> =
        repository.findByYearAndWeekAndDeletedIsFalse(year, week)
            .map { mapToDto(it) }

    override fun delete(id: Long) {
        repository.findById(id)
            .orElseThrow { EntityNotFoundException() }
            .apply { deleted = true }
            .run { repository.saveAndFlush(this) }
    }

    private fun mapToDto(shoppingItem: ShoppingItem): ShoppingItemDto = ShoppingItemDto(
        id = shoppingItem.id!!,
        product = shoppingItem.product,
        createdBy = shoppingItem.createdBy.username,
        createdAt = shoppingItem.createdAt,
    )

}
