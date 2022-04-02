package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MultipartBody

import okhttp3.RequestBody

import retrofit2.http.*


interface TrainingApi {

    //api request for apply to training
    @Multipart
    @POST("Training/apply_training")
    suspend fun applyTraining(
        @Part("sevarth_id") sevarth_id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("duration") duration: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
        @Part("org_name") org_name: RequestBody,
        @Part("organized_by") organized_by: RequestBody,
        @Part("training_status_id") training_status_id: RequestBody,
        @Part("org_id") org_id: RequestBody,
        @Part("department_id") department_id: RequestBody,
        @Part("training_type") training_type: RequestBody,
        @Part part: MultipartBody.Part
    ): Response<StatusResponse>

    //api request for apply to training
    @Multipart
    @POST("Training/add_completed_training")
    suspend fun add_completed_training(
        @Part("sevarth_id") sevarth_id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("duration") duration: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
        @Part("org_name") org_name: RequestBody,
        @Part("organized_by") organized_by: RequestBody,
        @Part("training_status_id") training_status_id: RequestBody,
        @Part("org_id") org_id: RequestBody,
        @Part("department_id") department_id: RequestBody,
        @Part("training_type") training_type: RequestBody,
        @Part part: MultipartBody.Part
    ): Response<StatusResponse>

    //api request for apply to training
    @Multipart
    @POST("Training/upload_training_certificate")
    suspend fun uploadTrainingCertificate(
        @Part("training_id") training_id: RequestBody,
        @Part part: MultipartBody.Part
    ): Response<StatusResponse>

    @GET("Training/get_training_types")
    suspend fun getTrainingTypes(): Response<List<TrainingTypes>>

    @FormUrlEncoded
    @POST("Training/update_training_status")
    suspend fun updateTrainingStatus(
        @Field("training_id") trainingId: String,
        @Field("training_status_id") trainingStatusId: String,
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("Training/get_trainings")
    suspend fun getAppliedTrainings(
        @Field("sevarth_id") sevarth_id: String
    ): Response<List<Training>>

    @FormUrlEncoded
    @POST("Training/get_trainings_by_hod")
    suspend fun getTrainingsByHod(
        @Field("hod_id") hod_id: String,
        @Field("status_id") statusId: String
    ): Response<List<Training>>

    @FormUrlEncoded
    @POST("Training/get_training_type")
    suspend fun getTrainingTypes(
        @Field("status_id") status_id: String
    ): Response<TrainingTypes>

    @FormUrlEncoded
    @POST("Training/get_trainings_by_principal")
    suspend fun getTrainingsByPrincipal(
        @Field("principal_id") principal_id: String,
        @Field("status_id") statusId: String
    ): Response<List<Training>>

    companion object{
        operator fun invoke(
        ) : TrainingApi {



            return Retrofit.Builder()
                .client(provideHttpClint(provideInterceptor()))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrainingApi::class.java)
        }

        fun provideInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        fun provideHttpClint(
            interceptor: HttpLoggingInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder().also {
                it.addInterceptor(interceptor)
            }.build()
    }

}

