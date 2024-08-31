package com.nabeelkm.finance.models

import java.time.LocalDateTime

data class Task(
    val title: String,
    val time: LocalDateTime,
    val amount: Int
)