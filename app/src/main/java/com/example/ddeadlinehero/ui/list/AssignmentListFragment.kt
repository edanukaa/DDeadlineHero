package com.example.ddeadlinehero.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ddeadlinehero.R
import com.example.ddeadlinehero.databinding.FragmentAssignmentListBinding
import com.example.ddeadlinehero.domain.model.Assignment
import com.example.ddeadlinehero.ui.addedit.AddEditAssignmentActivity
import kotlinx.coroutines.launch


class AssignmentListFragment : Fragment(R.layout.fragment_assignment_list) {

    private var _binding: FragmentAssignmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AssignmentListViewModel by viewModels {
        AssignmentListViewModel.Factory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAssignmentListBinding.bind(view)

        val adapter = AssignmentAdapter(
            showMarkCompleted = true,
            onMarkCompleted = { assignment ->
                viewModel.markAsCompleted(assignment.id)
            },
            onLongClick = { assignment ->
                showOptionsDialog(assignment)
            }
        )

        binding.rvAssignments.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAssignments.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.assignments.collect { list ->
                adapter.submitList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddEditAssignmentActivity::class.java))
        }
    }

    private fun openCompletedScreen() {

        Toast.makeText(
            requireContext(),
            "Went to completed page",
            Toast.LENGTH_SHORT
        ).show()

        requireActivity()
            .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                R.id.bottomNavigation
            )
            .selectedItemId = R.id.completedFragment
    }




    private fun showOptionsDialog(assignment: Assignment) {
        AlertDialog.Builder(requireContext())
            .setTitle("Assignment options")
            .setItems(arrayOf("Share", "Update", "Delete", "Go to Completed")) { _, which ->
                when (which) {
                    0 -> shareAssignment(assignment)
                    1 -> updateAssignment(assignment)
                    2 -> viewModel.deleteAssignment(assignment.id)
                    3 -> openCompletedScreen()

                }
            }
            .show()
    }

    private fun shareAssignment(assignment: Assignment) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Assignment: ${assignment.title}\nDue: ${assignment.dueDateText}"
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun updateAssignment(assignment: Assignment) {
        val intent = Intent(requireContext(), AddEditAssignmentActivity::class.java).apply {
            putExtra("assignmentId", assignment.id)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
