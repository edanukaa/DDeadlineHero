package com.example.ddeadlinehero.ui.addedit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ddeadlinehero.data.local.AssignmentLocalDatasource
import com.example.ddeadlinehero.data.repository.AssignmentRepository
import com.example.ddeadlinehero.domain.model.Assignment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// add, update
class AddEditViewModel(
    private val repository: AssignmentRepository
) : ViewModel() {

    fun addAssignment(assignment: Assignment) {
        viewModelScope.launch {
            repository.addAssignment(assignment)
        }
    }

    fun updateAssignment(assignment: Assignment) {
        viewModelScope.launch {
            repository.updateAssignment(assignment)
        }
    }


    fun getAssignmentOnce(id: Long, callback: (Assignment) -> Unit) {
        viewModelScope.launch {
            val list = repository.activeAssignments.first()
            val found = list.firstOrNull { it.id == id }
            if (found != null) callback(found)
        }
    }

    fun saveAssignment(assignment: Assignment) {
        viewModelScope.launch {
            repository.addAssignment(assignment)

            repository.postAssignment(
                title = assignment.title,
                dueDate = assignment.dueDateText
            )
        }
    }


    class Factory(
        private val context: Context
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val datasource = AssignmentLocalDatasource(context.applicationContext)
            val repository = AssignmentRepository(datasource)
            return AddEditViewModel(repository) as T
        }
    }


}
