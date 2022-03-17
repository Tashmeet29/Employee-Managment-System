package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.slideshow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.databinding.FragmentAppliedTrainingsBinding

class AppliedTrainingFragment : Fragment(R.layout.fragment_applied_trainings) {

    private lateinit var binding: FragmentAppliedTrainingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAppliedTrainingsBinding.bind(view)


    }

}