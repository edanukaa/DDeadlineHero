package com.example.ddeadlinehero.ui.completed

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ddeadlinehero.data.local.AssignmentLocalDatasource
import com.example.ddeadlinehero.data.repository.AssignmentRepository
import com.example.ddeadlinehero.domain.model.Assignment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// clear all
class CompletedViewModel(
    private val repository: AssignmentRepository
) : ViewModel() {


    val completedAssignments: Flow<List<Assignment>> =
        repository.completedAssignments


    fun clearAllCompleted() {
        viewModelScope.launch {
            repository.clearCompletedAssignments()
        }
    }


    class Factory(
        private val context: Context
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val localDatasource =
                AssignmentLocalDatasource(context.applicationContext)

            val repository =
                AssignmentRepository(localDatasource)

            return CompletedViewModel(repository) as T
        }
    }
}
