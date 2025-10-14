package com.rach.swipestore.data.local

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("productInfo")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productName: String?,
    val productType: String?,
    val price: String?,
    val tax: String?,
    val files: List<Uri>?
)