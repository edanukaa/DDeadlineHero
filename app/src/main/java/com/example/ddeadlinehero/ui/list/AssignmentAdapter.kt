package com.example.ddeadlinehero.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ddeadlinehero.databinding.ItemAssignmentBinding
import com.example.ddeadlinehero.domain.model.Assignment

class AssignmentAdapter(
    private val showMarkCompleted: Boolean,
    private val onMarkCompleted: (Assignment) -> Unit,
    private val onLongClick: (Assignment) -> Unit
) : ListAdapter<Assignment, AssignmentAdapter.VH>(AssignmentDiffCallback()) {

    inner class VH(
        private val binding: ItemAssignmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Assignment) {
            binding.tvTitle.text = item.title
            binding.tvDue.text = item.dueDateText

            binding.btnMarkCompleted.visibility =
                if (showMarkCompleted) ViewGroup.VISIBLE else ViewGroup.GONE

            binding.btnMarkCompleted.setOnClickListener {
                onMarkCompleted(item)
            }

            binding.root.setOnLongClickListener {
                onLongClick(item)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAssignmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
