package hu.bozgab.megabackend.repository

import hu.bozgab.megabackend.entity.ShoppingItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ShoppingItemRepository : JpaRepository<ShoppingItem, Long> {

    @Query(
        """
        SELECT 
            sI 
        FROM ShoppingItem sI
        WHERE YEAR(sI.createdAt) = :year 
            AND DATE_PART('WEEK', sI.createdAt) = :week
            AND sI.deleted = FALSE 
    """
    )
    fun findByYearAndWeekAndDeletedIsFalse(@Param("year") year: Int, @Param("week") week: Int): List<ShoppingItem>

}
