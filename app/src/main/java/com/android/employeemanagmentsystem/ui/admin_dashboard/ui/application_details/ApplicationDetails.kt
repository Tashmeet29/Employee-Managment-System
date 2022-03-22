package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.application_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentApplicationDetailsBinding
import com.android.employeemanagmentsystem.utils.getTrainingStatusById
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*

private const val TAG = "ApplicationDetails"

class ApplicationDetails : Fragment(R.layout.fragment_application_details) {

    private lateinit var binding: FragmentApplicationDetailsBinding
    private lateinit var training: Training

    private lateinit var trainingRepository: TrainingRepository
    private lateinit var trainingApi: TrainingApi
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    private lateinit var employee: Employee


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApplicationDetailsBinding.bind(view)

        training = arguments?.get("training") as Training
        trainingApi = TrainingApi.invoke()
        trainingRepository = TrainingRepository()
        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()




        setTrainingType(training.training_type)
        setValuesOnFields()
    }

    @SuppressLint("SetTextI18n")
    private fun setValuesOnFields() {

        GlobalScope.launch {
            employee = authRepository.getEmployee(employeeDao)

            val isHod = employee.role_id.toInt() == 2

            binding.apply {

                withContext(Dispatchers.Main) {

                    binding.LinearButtonLayout.isVisible =
                        (isHod && training.training_status_id == 1)

                    binding.LinearButtonLayout.isVisible =
                        (!isHod && training.training_status_id == 2)


                    btnApply.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "2",
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
                                    requireContext().toast(response.status)
                                    binding.progressBar.isVisible = false
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            } else {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "3",
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {

                                    binding.progressBar.isVisible = false

                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            }

                        }


                    }

                    btnDecline.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "4",
                                    trainingApi
                                )

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            } else {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }
                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "5",
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }

                            }

                        }


                    }
                }

                withContext(Dispatchers.Main) {
                    tvTrainingName.text = training.name
                    tvOrganizationName.text =
                        if (training.org_name.isBlank()) training.org_name else training.organized_by

                    tvApplyLetter.text = training.apply_letter

                    tvDuration.text =
                        getDurationInWeeks(training.duration) + "  (" + training.start_date + " to " + training.end_date + ")"
                    etTrainingStatus.text = training.training_status_id.getTrainingStatusById()
                }

            }
        }


    }

    private fun setTrainingType(statusId: String) {
        GlobalScope.launch {
            val training: TrainingTypes = trainingRepository.getTrainingTypes(trainingApi, statusId)
            withContext(Dispatchers.Main) {
                binding.etTrainingType.text = training.name

            }
        }
    }

    private fun getDurationInWeeks(days: String): String {
        val day: Int = days.toInt()

        return if (day < 7) "$days days "
        else {
            val week = (day % 365) / 7
            "$week weeks"
        }
    }
}