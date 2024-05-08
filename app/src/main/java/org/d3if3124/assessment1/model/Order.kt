package org.d3if3124.assessment1.model

import androidx.annotation.StringRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @Embedded val minuman: Minuman?,
    val jumlah: Int,
    @StringRes val size: Int,
    val totalHarga: Float

)