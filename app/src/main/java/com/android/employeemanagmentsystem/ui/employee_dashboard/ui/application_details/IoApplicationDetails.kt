package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.application_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.databinding.FragmentIoApplicationDetailsBinding

class IoApplicationDetails: Fragment(R.layout.fragment_io_application_details) {

    private lateinit var binding: FragmentIoApplicationDetailsBinding
    private lateinit var application: Application

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentIoApplicationDetailsBinding.bind(view)

        application = arguments?.get("Application") as Application

        setApplicationData()
    }

    private fun setApplicationData() {
        binding.apply {

            tvIoTitle.text = application.title
            tvDesc.text = application.remark
            tvDate.text = application.date
            tvPdf.text = application.application
            tvApplicationType.text = application.getApplicationStringType

        }
    }

}