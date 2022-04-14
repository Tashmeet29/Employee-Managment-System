package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.Application
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


interface IOApplicationApi {

    //api request for apply to training
    @Multipart
    @POST("IOApplication/apply_io_application")
    suspend fun applyIOApplication(
        @Part("sevarth_id") sevarth_id: RequestBody,
        @Part("title") title: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part("date") date: RequestBody,
        @Part("org_id") org_id: RequestBody,
        @Part("department_id") department_id: RequestBody,
        @Part("application_type") application_type: RequestBody,
        @Part part: MultipartBody.Part
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("IoApplication/get_applications")
    suspend fun getAppliedApplications(
        @Field("sevarth_id") sevarth_id: String
    ): Response<List<Application>>

    @FormUrlEncoded
    @POST("IoApplication/get_application_by_id")
    suspend fun getApplication(
        @Field("application_id") application_id: String
    ): Response<Application>

    companion object{
        operator fun invoke(
        ) : IOApplicationApi {



            return Retrofit.Builder()
                .client(provideHttpClint(provideInterceptor()))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IOApplicationApi::class.java)
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

