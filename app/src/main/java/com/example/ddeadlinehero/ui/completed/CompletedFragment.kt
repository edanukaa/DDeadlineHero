package com.example.ddeadlinehero.ui.completed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ddeadlinehero.R
import com.example.ddeadlinehero.databinding.FragmentCompletedBinding
import com.example.ddeadlinehero.ui.list.AssignmentAdapter
import kotlinx.coroutines.launch

class CompletedFragment :
    Fragment(R.layout.fragment_completed) {

    private lateinit var binding: FragmentCompletedBinding


    private val args: CompletedFragmentArgs by navArgs()

    private val viewModel: CompletedViewModel by viewModels {
        CompletedViewModel.Factory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCompletedBinding.bind(view)


        if (args.fromHome) {
            Toast.makeText(
                requireContext(),
                "Opened from Home to Completed",
                Toast.LENGTH_SHORT
            ).show()
        }

        val adapter = AssignmentAdapter(
            showMarkCompleted = false,
            onMarkCompleted = {},
            onLongClick = {}

        )


        binding.rvCompleted.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCompleted.adapter = adapter

        binding.btnClearAll.setOnClickListener {
            viewModel.clearAllCompleted()
        }


        lifecycleScope.launch {
            viewModel.completedAssignments.collect {
                adapter.submitList(it)
            }
        }
    }
}
