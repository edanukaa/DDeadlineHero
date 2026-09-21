package com.example.ddeadlinehero.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val title: String,
    val dueDate: String,

    val isCompleted: Boolean = false,

    // completed items
    val completedAt: Long? = null
)
