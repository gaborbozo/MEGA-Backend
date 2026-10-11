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
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.WeekFields

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
            createdBy = megaUser,
            repeatable = request.repeatable
        )

        return mapToDto(repository.saveAndFlush(shoppingItem))
    }

    override fun getByYearAndWeek(year: Int, week: Int): List<ShoppingItemDto> {
        val weekFields = WeekFields.ISO
        val weekStart = LocalDate.of(year, 1, 4)
            .with(weekFields.weekBasedYear(), year.toLong())
            .with(weekFields.weekOfWeekBasedYear(), week.toLong())
            .with(weekFields.dayOfWeek(), 1)
        require(
            weekStart.get(weekFields.weekBasedYear()) == year &&
                weekStart.get(weekFields.weekOfWeekBasedYear()) == week
        ) { "Invalid ISO week $week for year $year" }

        val start = weekStart.atStartOfDay(ZoneOffset.UTC).toInstant()
        val end = weekStart.plusWeeks(1).atStartOfDay(ZoneOffset.UTC).toInstant()
        val previousStart = weekStart.minusWeeks(1).atStartOfDay(ZoneOffset.UTC).toInstant()

        repository.findRepeatableByCreatedAtRangeForUpdate(previousStart, start)
            .forEach { previousItem ->
                previousItem.repeatable = false
                repository.save(previousItem)
                repository.save(
                    ShoppingItem(
                        product = previousItem.product,
                        createdBy = previousItem.createdBy,
                        repeatable = true
                    )
                )
            }

        return repository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThanAndDeletedIsFalse(start, end)
            .map { mapToDto(it) }
    }

    override fun dropRepeat(id: Long) {
        repository.findById(id)
            .orElseThrow { EntityNotFoundException() }
            .apply { repeatable = false }
            .run { repository.saveAndFlush(this) }
    }

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
        repeatable = shoppingItem.repeatable,
    )

}
