package org.d3if3124.assessment1.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3124.assessment1.database.OrderDao
import org.d3if3124.assessment1.model.Order
import java.util.Date

class DetailViewModel(private val dao: OrderDao) : ViewModel() {

    fun insert(
        namaPembeli: String,
        minuman: Int,
        jumlah: Int,
        size: Int,
        totalHarga: Float
    ) {
        val order = Order(
            namaPembeli = namaPembeli,
            minuman = minuman,
            jumlah = jumlah,
            size = size,
            totalHarga = totalHarga,
            tanggal = Date()
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(order)
        }
    }

    suspend fun getOrder(id: Long): Order? {
        return dao.getOrderById(id)
    }

    fun update(
        id: Long,
        namaPembeli: String,
        minuman: Int,
        jumlah: Int,
        size: Int,
        totalHarga: Float
    ) {
        val order = Order(
            id = id,
            namaPembeli = namaPembeli,
            minuman = minuman,
            jumlah = jumlah,
            size = size,
            totalHarga = totalHarga,
            tanggal = Date()
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(order)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}