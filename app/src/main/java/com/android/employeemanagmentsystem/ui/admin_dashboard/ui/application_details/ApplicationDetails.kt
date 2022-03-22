package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.application_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentApplicationDetailsBinding
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





        setValuesOnFields()
    }

    private fun setValuesOnFields() {

        GlobalScope.launch {
            employee = authRepository.getEmployee(employeeDao)

            val isHod = employee.role_id.toInt() == 2

            binding.apply {

                withContext(Dispatchers.Main){
                    etTrainingName.text = training.name


                    btnApply.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {
                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "2",
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            } else {
                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "3",
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
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
                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "4",
                                    trainingApi
                                )

                                withContext(Dispatchers.Main) {
                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            } else {
                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    "5",
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
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

            }
        }



    }
}