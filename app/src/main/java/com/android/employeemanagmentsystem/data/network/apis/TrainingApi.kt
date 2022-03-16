package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
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
        @Part part: MultipartBody.Part
    ): Response<StatusResponse>



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

