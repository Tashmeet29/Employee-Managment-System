package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.training

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.databinding.FragmentApplyTrainingBinding

class ApplyTrainingFragment : Fragment(R.layout.fragment_apply_training) {

    private lateinit var binding: FragmentApplyTrainingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApplyTrainingBinding.bind(view)


    }
}