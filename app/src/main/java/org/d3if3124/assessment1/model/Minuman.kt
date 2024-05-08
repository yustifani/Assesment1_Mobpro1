package org.d3if3124.assessment1.model

import androidx.annotation.DrawableRes

data class Minuman(
    val id: Long,
    val nama: String,
    @DrawableRes val imageResId: Int,
    val harga: Float,
    val totalHarga: Float? = 0f,
)