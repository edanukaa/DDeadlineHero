package com.example.ddeadlinehero.data.repository

import android.util.Log
import com.example.ddeadlinehero.data.local.AssignmentLocalDatasource
import com.example.ddeadlinehero.data.remote.CreateAssignmentRequest
import com.example.ddeadlinehero.data.remote.RetrofitClient
import com.example.ddeadlinehero.domain.model.Assignment
import kotlinx.coroutines.flow.Flow

class AssignmentRepository(
    private val localDatasource: AssignmentLocalDatasource
) {


    val activeAssignments: Flow<List<Assignment>> =
        localDatasource.loadActiveAssignments()


    val completedAssignments: Flow<List<Assignment>> =
        localDatasource.loadCompletedAssignments()

    suspend fun addAssignment(assignment: Assignment) {
        localDatasource.insertAssignment(assignment)
    }

    suspend fun markCompleted(id: Long) {
        localDatasource.markAsCompleted(id)
    }


    suspend fun deleteAssignment(id: Long) {
        localDatasource.deleteAssignment(id)
    }


    suspend fun updateAssignment(assignment: Assignment) {
        localDatasource.updateAssignment(assignment)
    }

    suspend fun postAssignment(title: String, dueDate: String) {
        try {
            val response = RetrofitClient.api.createAssignment(
                CreateAssignmentRequest(
                    title = title,
                    dueDate = dueDate
                )
            )
            Log.d("NETWORK", "POST sent: $response")
        } catch (e: Exception) {
            Log.e("NETWORK", "POST failed safely: ${e.message}")
        }
    }
    suspend fun clearCompletedAssignments() {
        localDatasource.deleteAllCompleted()
    }

}
