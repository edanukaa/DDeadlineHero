package com.example.ddeadlinehero.ui.addedit

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ddeadlinehero.databinding.ActivityAddEditAssignmentBinding
import com.example.ddeadlinehero.domain.model.Assignment
import java.util.Calendar

class AddEditAssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditAssignmentBinding

    private val viewModel: AddEditViewModel by viewModels {
        AddEditViewModel.Factory(this)
    }

    private var assignmentId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        assignmentId = intent.getLongExtra("assignmentId", -1L).takeIf { it != -1L }

        if (assignmentId != null) {
            binding.toolbar.title = "Update Assignment"

            viewModel.getAssignmentOnce(assignmentId!!) { assignment ->
                binding.inputTitle.setText(assignment.title)
                binding.inputDueDate.setText(assignment.dueDateText)
            }
        } else {
            binding.toolbar.title = "Add Assignment"
        }

        // Kjo osht per date / due date
        binding.inputDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate =
                        "%02d.%02d.%04d".format(
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )

                    binding.inputDueDate.setText(formattedDate)
                },
                year,
                month,
                day
            )

            datePicker.show()
        }

        binding.btnSave.setOnClickListener {

            val title = binding.inputTitle.text.toString().trim()
            val dueDate = binding.inputDueDate.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dueDate.isEmpty()) {
                Toast.makeText(this, "Please select a due date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val assignment = Assignment(
                id = assignmentId ?: 0L,
                title = title,
                dueDateText = dueDate,
                isCompleted = false
            )

            if (assignmentId == null) {
                viewModel.saveAssignment(assignment)
                Toast.makeText(this, "Assignment added", Toast.LENGTH_SHORT).show()
            } else {
                viewModel. updateAssignment(assignment)
                Toast.makeText(this, "Assignment updated", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}
