package com.android.employeemanagmentsystem.ui.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityApplyTrainingBinding
import com.android.employeemanagmentsystem.databinding.ActivityMainBinding
import com.android.employeemanagmentsystem.utils.changeStatusBarColor
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ApplyTraining : AppCompatActivity() {

    private lateinit var binding: ActivityApplyTrainingBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var trainingRepo: TrainingRepository
    private lateinit var trainingApi: TrainingApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplyTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.changeStatusBarColor()

        handleClickOfApplyTrainingButton()

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(this).getEmployeeDao()
        trainingRepo = TrainingRepository()
        trainingApi = TrainingApi()

    }

    private fun handleClickOfApplyTrainingButton() {

        binding.apply {

            btnSubmit.setOnClickListener {

                val training_name = "abc"
                val organization_name = "abc"
                val organized_by = "abc"
                val training_duration = "abc"
                val training_start_date = "abc"
                val training_end_date = "abc"
                val training_apply_to = "acb"

                when{

                    training_name.isBlank() -> toast("Please Enter Training Name")
                    organization_name.isBlank() -> toast("Please Enter organization name")
                    training_duration.isBlank() -> toast("Please Enter Training Duration")
                    training_start_date.isBlank() -> toast("Please Select Start Date")
                    training_end_date.isBlank() -> toast("Please Select End Name")

                    else -> {

                        GlobalScope.launch {
                            //getting employee details from room database
                            val employee = authRepository.getEmployee(employeeDao)

                            val trainingId =  trainingRepo.applyTraining(
                                sevarth_id = employee.sevarth_id,
                                name = training_name,
                                duration = training_duration,
                                start_date = training_start_date,
                                end_date = training_end_date,
                                org_name = organization_name,
                                organized_by = organized_by,
                                org_id = employee.org_id.toString(),
                                department_id = employee.dept_id.toString(),
                                trainingApi = trainingApi,
                                training_status_id = "1" //todo need to change here
                            )


                        }


                    }
                }

            }

        }

    }
}