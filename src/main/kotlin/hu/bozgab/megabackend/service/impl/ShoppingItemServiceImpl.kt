package hu.bozgab.megabackend.service.impl

import hu.bozgab.megabackend.dto.ShoppingItemDTO
import hu.bozgab.megabackend.dto.request.CreateShoppingItemRequest
import hu.bozgab.megabackend.entity.ShoppingItem
import hu.bozgab.megabackend.repository.MegaUserRepository
import hu.bozgab.megabackend.repository.ShoppingItemRepository
import hu.bozgab.megabackend.service.ShoppingItemService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ShoppingItemServiceImpl(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val megaUserRepository: MegaUserRepository
) : ShoppingItemService {

    override fun create(userId: Long, request: CreateShoppingItemRequest): ShoppingItemDTO {
        val megaUser = megaUserRepository.findById(userId)
            .orElseThrow { NotFoundException() }

        val shoppingItem = ShoppingItem(
            product = request.product,
            createdBy = megaUser
        )

        return mapToDTO(shoppingItemRepository.saveAndFlush(shoppingItem))
    }

    override fun getByYearAndWeek(year: Int, week: Int): List<ShoppingItemDTO> =
        shoppingItemRepository.findByYearAndWeekAndDeletedIsFalse(year, week)
            .map { mapToDTO(it) }

    override fun delete(id: Long) {
        shoppingItemRepository.findById(id)
            .orElseThrow { NotFoundException() }
            .apply { deleted = true }
            .run { shoppingItemRepository.saveAndFlush(this) }
    }

    private fun mapToDTO(shoppingItem: ShoppingItem): ShoppingItemDTO = ShoppingItemDTO(
        id = shoppingItem.id!!,
        product = shoppingItem.product,
        createdBy = shoppingItem.createdBy.username,
        createdAt = shoppingItem.createdAt,
    )

}
