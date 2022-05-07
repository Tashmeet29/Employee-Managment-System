package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.add_user_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.network.apis.IOApplicationApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.IOApplicationRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.databinding.FragmentAddUserDetailsBinding
import com.android.employeemanagmentsystem.databinding.FragmentIoApplicationDetailsBinding

class AddUserDetailsFragment: Fragment(R.layout.fragment_add_user_details) {

    private lateinit var binding: FragmentAddUserDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddUserDetailsBinding.bind(view)

    }

}