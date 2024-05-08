package org.d3if3124.assessment1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3124.assessment1.model.Order

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Query("SELECT * FROM `order` ORDER BY tanggal DESC")
    fun getOrder(): Flow<List<Order>>
}