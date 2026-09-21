package com.example.ddeadlinehero.data.local

import android.content.Context
import com.example.ddeadlinehero.domain.model.Assignment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AssignmentLocalDatasource(
    context: Context
) {

    private val dao = AppDatabase
        .getInstance(context)
        .assignmentDao()


    fun loadActiveAssignments(): Flow<List<Assignment>> {
        return dao.loadActiveAssignments()
            .map { entities ->
                entities.map { entity ->
                    Assignment(
                        id = entity.id,
                        title = entity.title,
                        dueDateText = entity.dueDate,
                        isCompleted = entity.isCompleted
                    )
                }
            }
    }


    fun loadCompletedAssignments(): Flow<List<Assignment>> {
        return dao.loadCompletedAssignments()
            .map { entities ->
                entities.map { entity ->
                    Assignment(
                        id = entity.id,
                        title = entity.title,
                        dueDateText = entity.dueDate,
                        isCompleted = entity.isCompleted
                    )
                }
            }
    }


    suspend fun insertAssignment(assignment: Assignment) {
        dao.insertAssignment(
            AssignmentEntity(
                title = assignment.title,
                dueDate = assignment.dueDateText
            )
        )
    }


    suspend fun markAsCompleted(id: Long) {
        dao.markAsCompleted(
            assignmentId = id,
            completedAt = System.currentTimeMillis()
        )
    }

    suspend fun deleteAssignment(id: Long) {
        dao.deleteAssignment(id)
    }

    suspend fun updateAssignment(assignment: Assignment) {
        dao.updateAssignment(
            AssignmentEntity(
                id = assignment.id,
                title = assignment.title,
                dueDate = assignment.dueDateText,
                isCompleted = assignment.isCompleted
            )
        )
    }

    suspend fun deleteAllCompleted() {
        dao.deleteAllCompleted()
    }
}
