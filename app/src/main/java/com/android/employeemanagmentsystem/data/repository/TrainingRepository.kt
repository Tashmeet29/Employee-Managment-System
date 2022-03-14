package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Field

class TrainingRepository : SafeApiRequest() {

    suspend fun applyTraining(
         sevarth_id: String,
         name: String,
         duration: String,
         start_date: String,
         end_date: String,
         org_name: String,
         organized_by: String,
         training_status_id: String,
         org_id: String,
         department_id: String,
         trainingApi: TrainingApi
    ) = apiRequest {
        trainingApi.applyTraining(
            sevarth_id, name, duration, start_date, end_date, org_name, organized_by, training_status_id, org_id, department_id
        )
    }


}