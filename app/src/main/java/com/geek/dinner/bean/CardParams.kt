package com.geek.dinner.bean

/**
 * 卡片数据
 */
data class CardParams(
    val name: String,
    val dateStr: String,
    val timeStr: String,
    val type: Int,
    val isActive: Boolean,
)
