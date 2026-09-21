package com.example.ddeadlinehero.domain.model

data class Assignment(
    val id: Long = 0L,
    val title: String,
    val dueDateText: String,
    val isCompleted: Boolean = false
)
