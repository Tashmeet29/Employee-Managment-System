package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_applications

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.IOApplicationApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.IOApplicationRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.databinding.FragmentAppliedApplicationsBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_trainings.AppliedTrainingFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppliedApplicationsFragments: Fragment(R.layout.fragment_applied_applications) {

    private lateinit var binding: FragmentAppliedApplicationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAppliedApplicationsBinding.inflate(layoutInflater)
        getApplications()

    }


    private fun getApplications() {
        val authRepository = AuthRepository()
        val ioApplicationRepository = IOApplicationRepository()
        val ioApplicationApi = IOApplicationApi.invoke()

        val empDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

        binding.progressBar.isVisible = true



        GlobalScope.launch {
            val sevarthId = authRepository.getEmployee(empDao).sevarth_id

            val applications: List<Application> =
                ioApplicationRepository.getAppliedApplications(sevarthId, ioApplicationApi)

            withContext(Dispatchers.Main) {

                binding.tvNotAvailable.isVisible = applications.isEmpty()

//                binding.recyclerView.apply {
//                    adapter = TrainingsAdapter(applications, this@AppliedApplicationsFragments)
//                    layoutManager = LinearLayoutManager(requireContext())
//                }

                binding.progressBar.isVisible = false



            }

        }


    }


//
//
//    override fun onTrainingItemClicked(training: Training) {
//        AppliedTrainingFragmentDirections.actionNavAppliedTrainingsToTrainingCompletionFragment(
//            training
//        ).apply {
//            findNavController().navigate(this)
//        }
//    }

}