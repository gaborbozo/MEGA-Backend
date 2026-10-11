package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.ShoppingItem
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ShoppingItemRepository : JpaRepository<ShoppingItem, Long> {

    fun findByCreatedAtGreaterThanEqualAndCreatedAtLessThanAndDeletedIsFalse(
        start: Instant,
        end: Instant
    ): List<ShoppingItem>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        SELECT sI
        FROM ShoppingItem sI
        WHERE sI.createdAt >= :start
            AND sI.createdAt < :end
            AND sI.deleted = FALSE
            AND sI.repeatable = TRUE
        """
    )
    fun findRepeatableByCreatedAtRangeForUpdate(
        @Param("start") start: Instant,
        @Param("end") end: Instant
    ): List<ShoppingItem>

}
