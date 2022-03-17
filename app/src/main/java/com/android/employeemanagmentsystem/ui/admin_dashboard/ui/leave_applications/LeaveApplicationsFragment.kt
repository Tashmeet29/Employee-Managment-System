package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.leave_applications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.databinding.FragmentLeaveApplicationsBinding

class LeaveApplicationsFragment: Fragment(R.layout.fragment_leave_applications){

    private lateinit var binding: FragmentLeaveApplicationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLeaveApplicationsBinding.bind(view)


    }

}