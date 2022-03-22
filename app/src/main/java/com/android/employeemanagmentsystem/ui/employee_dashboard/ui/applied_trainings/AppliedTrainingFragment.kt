package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_trainings

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
import com.android.employeemanagmentsystem.databinding.FragmentAppliedTrainingsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppliedTrainingFragment : Fragment(R.layout.fragment_applied_trainings),
    TrainingsAdapter.TrainingClickListener {

    private lateinit var binding: FragmentAppliedTrainingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAppliedTrainingsBinding.bind(view)

        getTrainings()

    }


    private fun getTrainings() {
        val authRepository = AuthRepository()
        val trainingRepository = TrainingRepository()
        val trainingApi = TrainingApi()

        val empDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

        binding.progressBar.isVisible = true

        GlobalScope.launch {
            val sevarthId = authRepository.getEmployee(empDao).sevarth_id

            val trainings: List<Training> =
                trainingRepository.getAppliedTrainings(sevarthId, trainingApi)

            withContext(Dispatchers.Main) {

                binding.tvNotAvailable.isVisible = trainings.isEmpty()

                binding.recyclerView.apply {
                    adapter = TrainingsAdapter(trainings, this@AppliedTrainingFragment)
                    layoutManager = LinearLayoutManager(requireContext())
                }

                binding.progressBar.isVisible = false
            }

        }


    }

    override fun onTrainingItemClicked(training: Training) {
        AppliedTrainingFragmentDirections.actionNavAppliedTrainingsToTrainingCompletionFragment(
            training
        ).apply {
            findNavController().navigate(this)
        }
    }

}