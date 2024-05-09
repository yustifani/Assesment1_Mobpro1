package org.d3if3124.assessment1.model

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaPembeli: String,
    val minuman: Int,
    val jumlah: Int,
    @StringRes val size: Int,
    val totalHarga: Float,
    val tanggal: Date,
)