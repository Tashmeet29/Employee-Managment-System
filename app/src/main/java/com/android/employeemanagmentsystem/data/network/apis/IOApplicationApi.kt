package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.*
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

    @GET("IOApplication/get_departments")
    suspend fun getDepartments(): Response<List<Departments>>

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
        @Part("from_department") from_department: RequestBody,
        @Part("role_id") role_id: RequestBody,
        @Part("applicant_name") applicant_name: RequestBody,
        @Part part: MultipartBody.Part
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("IoApplication/get_employee_details")
    suspend fun getEmployeeDetails(
        @Field("sevarth_id") sevarth_id: String,
    ): Response<EmployeeDetails>

    @FormUrlEncoded
    @POST("IoApplication/get_applications")
    suspend fun getAppliedApplications(
        @Field("sevarth_id") sevarth_id: String,
        @Field("role_id") role_id: String
    ): Response<List<Application>>

    @FormUrlEncoded
    @POST("IoApplication/get_application_by_id")
    suspend fun getApplication(
        @Field("application_id") application_id: String
    ): Response<Application>

    @FormUrlEncoded
    @POST("IoApplication/update_status_id")
    suspend fun updateStatusId(
        @Field("application_id") application_id: String,
        @Field("status_id") status_id: String,
        @Field("remark") remark: String,
    ): Response<StatusResponse>

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

