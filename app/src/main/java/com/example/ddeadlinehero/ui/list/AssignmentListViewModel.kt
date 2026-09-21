package com.example.ddeadlinehero.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ddeadlinehero.data.local.AssignmentLocalDatasource
import com.example.ddeadlinehero.data.repository.AssignmentRepository
import com.example.ddeadlinehero.domain.model.Assignment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// mark completed, delete
class AssignmentListViewModel(
    private val repo: AssignmentRepository
) : ViewModel() {

    val assignments: Flow<List<Assignment>> = repo.activeAssignments


    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun markAsCompleted(id: Long) {
        viewModelScope.launch {
            repo.markCompleted(id)
            _uiEvent.emit("Assignment marked as completed")
        }
    }

    fun deleteAssignment(id: Long) {
        viewModelScope.launch {
            repo.deleteAssignment(id)
            _uiEvent.emit("Assignment deleted")
        }
    }

    class Factory(
        private val context: Context
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val localDatasource = AssignmentLocalDatasource(context.applicationContext)
            val repository = AssignmentRepository(localDatasource)
            return AssignmentListViewModel(repository) as T
        }
    }
}
