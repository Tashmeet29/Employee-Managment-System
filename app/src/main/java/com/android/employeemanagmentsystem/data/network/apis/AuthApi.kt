package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.EmployeeDetails
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface AuthApi {

    //api request for login of all employees of all roles
    @FormUrlEncoded
    @POST("Authentication/login")
    suspend fun employeeLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<Employee>

    @FormUrlEncoded
    @POST("get_fp_question")
    suspend fun emailValidate(
        @Field("email") email: String,
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("validate_answer")
    suspend fun answerValidate(
        @Field("email") email: String,
        @Field("answer") answer: String,
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("reset_password")
    suspend fun changePassword(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<StatusResponse>

    //api request for login of all employees of all roles
    @GET("EmployeeDetails/getDetails")
    suspend fun getEmployeeDetails(
        @Query("sevarth_id") sevarth_id: String
    ): Response<EmployeeDetails>


    companion object {
        operator fun invoke(
            //networkConnectionInterceptor: NetworkConnectionInterceptor
        ): AuthApi {

//            val okkHttpclient = OkHttpClient.Builder()
////                .addInterceptor(networkConnectionInterceptor)
//                .build()

            return Retrofit.Builder()
                //.client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthApi::class.java)
        }
    }


}

