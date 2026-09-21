package com.example.ddeadlinehero.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ddeadlinehero.R
import com.example.ddeadlinehero.databinding.FragmentSettingsBinding
import com.example.ddeadlinehero.service.DeadlineReminderService
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var binding: FragmentSettingsBinding? = null

    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModel.Factory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = FragmentSettingsBinding.bind(view)
        binding = b


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notificationsEnabled.collect { enabled ->
                b.switchNotifications.setOnCheckedChangeListener(null)
                b.switchNotifications.isChecked = enabled
                setupListener(b)
            }
        }
    }

    private fun setupListener(b: FragmentSettingsBinding) {
        b.switchNotifications.setOnCheckedChangeListener { _, isChecked ->


            viewModel.onNotificationsToggled(isChecked)


            val intent =
                Intent(requireContext(), DeadlineReminderService::class.java)

            if (isChecked) {
                requireContext().startService(intent)
            } else {
                requireContext().stopService(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
