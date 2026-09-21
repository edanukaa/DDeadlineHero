package com.example.ddeadlinehero.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.example.ddeadlinehero.domain.model.Assignment

class AssignmentDiffCallback : DiffUtil.ItemCallback<Assignment>() {

    override fun areItemsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
        return oldItem == newItem
    }
}

