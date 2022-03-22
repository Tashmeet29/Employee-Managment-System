package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.databinding.FragmentTrainingApplicationsBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_trainings.AppliedTrainingFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingApplicationsFragment: Fragment(R.layout.fragment_training_applications), AppliedTrainingsAdapter.TrainingClickListener {

    private lateinit var binding: FragmentTrainingApplicationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrainingApplicationsBinding.bind(view)

        getTrainings()
    }

    private fun getTrainings() {
        val authRepository = AuthRepository()
        val trainingRepository = TrainingRepository()
        val trainingApi = TrainingApi()

        val empDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

        binding.progressBar.isVisible = true

        GlobalScope.launch {
            val employee = authRepository.getEmployee(empDao)

            val trainings: List<Training> = trainingRepository.getAppliedTrainingsByAdmin(employee.role_id.toInt(), employee.sevarth_id,  trainingApi)

            withContext(Dispatchers.Main){

                binding.progressBar.isVisible = false

                binding.tvNotAvailable.isVisible = trainings.isEmpty()

                binding.recyclerView.apply {
                    adapter = AppliedTrainingsAdapter(trainings, this@TrainingApplicationsFragment)
                    layoutManager = LinearLayoutManager(requireContext())
                }


            }

        }


    }

    override fun onTrainingItemClicked(training: Training) {
        TrainingApplicationsFragmentDirections.actionNavTrainingApplicationsToApplicationDetails(training).apply {
            findNavController().navigate(this)
        }
    }

}