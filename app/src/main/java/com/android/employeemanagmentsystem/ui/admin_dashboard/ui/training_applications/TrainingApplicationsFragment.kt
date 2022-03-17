package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.databinding.FragmentTrainingApplicationsBinding

class TrainingApplicationsFragment: Fragment(R.layout.fragment_training_applications) {

    private lateinit var binding: FragmentTrainingApplicationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrainingApplicationsBinding.bind(view)


    }
}