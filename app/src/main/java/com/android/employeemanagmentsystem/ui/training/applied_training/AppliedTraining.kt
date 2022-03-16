package com.android.employeemanagmentsystem.ui.training.applied_training

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.databinding.ActivityAppliedTrainingsBinding
import com.android.employeemanagmentsystem.ui.training.training_completion.TrainingCompletion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AppliedTraining"
class AppliedTraining: AppCompatActivity(), TrainingsAdapter.TrainingClickListener {

    private lateinit var binding: ActivityAppliedTrainingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppliedTrainingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getTrainings()

    }

    private fun getTrainings() {
        val authRepository = AuthRepository()
        val trainingRepository = TrainingRepository()
        val trainingApi = TrainingApi()

        val empDao = AppDatabase.invoke(applicationContext).getEmployeeDao()
        GlobalScope.launch {
            val sevarthId = authRepository.getEmployee(empDao).sevarth_id

            val trainings: List<Training> = trainingRepository.getAppliedTrainings(sevarthId, trainingApi)

            withContext(Dispatchers.Main){
                binding.recyclerView.apply {
                    adapter = TrainingsAdapter(trainings, this@AppliedTraining)
                    layoutManager = LinearLayoutManager(this@AppliedTraining)
                }
            }

        }


    }

    override fun onTrainingItemClicked(training: Training) {
        val intent = Intent(this, TrainingCompletion::class.java)
        intent.putExtra(TrainingCompletion.INTENT_TRAINING_ID, training.id)
        startActivity(intent)
    }
}