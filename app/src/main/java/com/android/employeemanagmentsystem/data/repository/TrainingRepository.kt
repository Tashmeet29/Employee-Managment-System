package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.utils.toMultipartReq
import okhttp3.MultipartBody

class TrainingRepository : SafeApiRequest() {

    //converting strings to multipart response
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
        applyPdf: MultipartBody.Part,
        trainingApi: TrainingApi
    ) = apiRequest {
        trainingApi.applyTraining(
            sevarth_id.toMultipartReq(),
            name.toMultipartReq(),
            duration.toMultipartReq(),
            start_date.toMultipartReq(),
            end_date.toMultipartReq(),
            org_name.toMultipartReq(),
            organized_by.toMultipartReq(),
            training_status_id.toMultipartReq(),
            org_id.toMultipartReq(),
            department_id.toMultipartReq(),
            applyPdf
        )
    }




}