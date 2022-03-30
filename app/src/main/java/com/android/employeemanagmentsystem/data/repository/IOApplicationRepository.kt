package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.IOApplicationApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.utils.toMultipartReq
import okhttp3.MultipartBody

class IOApplicationRepository : SafeApiRequest() {

    //converting strings to multipart response
    suspend fun applyIOApplication(
        sevarth_id: String,
        title: String,
        desc: String,
        date: String,
        org_id: String,
        department_id: String,
        applyPdf: MultipartBody.Part,
        iOApplicationApi: IOApplicationApi
    ) = apiRequest {
        iOApplicationApi.applyIOApplication(
            sevarth_id.toMultipartReq(),
            title.toMultipartReq(),
            desc.toMultipartReq(),
            date.toMultipartReq(),
            org_id.toMultipartReq(),
            department_id.toMultipartReq(),
            applyPdf
        )
    }

    suspend fun getTrainingTypes(trainingApi: TrainingApi, statusId: String) = apiRequest { trainingApi.getTrainingTypes(statusId)}

    suspend fun getTrainingTypes(trainingApi: TrainingApi) = apiRequest { trainingApi.getTrainingTypes() }

    //converting strings to multipart response
    suspend fun uploadTrainingCertificate(
        trainingId: String,
        conpletionPdf: MultipartBody.Part,
        trainingApi: TrainingApi
    ) = apiRequest {
        trainingApi.uploadTrainingCertificate(
            trainingId.toMultipartReq(),
            conpletionPdf
        )
    }


    suspend fun getAppliedTrainings(
        sevarth_id: String,
        trainingApi: TrainingApi
    ) = apiRequest {
        trainingApi.getAppliedTrainings(sevarth_id)
    }

    suspend fun getAppliedTrainingsByAdmin(
        roleId: Int,
        sevarth_id: String,
        trainingApi: TrainingApi
    ): List<Training> {
        if (roleId == 2) return apiRequest { trainingApi.getTrainingsByHod(sevarth_id) }
        else return apiRequest { trainingApi.getTrainingsByPrincipal(sevarth_id) }
    }

    suspend fun updateTrainingStatus(
        trainingId: String,
        trainingStatusId: String,
        trainingApi: TrainingApi
    ) = apiRequest { trainingApi.updateTrainingStatus(trainingId, trainingStatusId) }


}