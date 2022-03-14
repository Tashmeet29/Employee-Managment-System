package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.ApplyTrainingResponse
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.utils.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TrainingApi {

    //api request for login of all employees of all roles
    @FormUrlEncoded
    @POST("Training/apply_training")
    suspend fun applyTraining(
        @Field("severth_id") sevarth_id: String,
        @Field("name") name: String,
        @Field("duration") duration: String,
        @Field("start_date") start_date: String,
        @Field("end_date") end_date: String,
        @Field("org_name") org_name: String,
        @Field("organized_by") organized_by: String,
        @Field("training_status_id") training_status_id: String,
        @Field("org_id") org_id: String,
        @Field("department_id") department_id: String
    ): Response<ApplyTrainingResponse>


    companion object{
        operator fun invoke(
            //networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : TrainingApi {

//            val okkHttpclient = OkHttpClient.Builder()
//                .addInterceptor(networkConnectionInterceptor)
//                .build()

            return Retrofit.Builder()
                //.client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TrainingApi::class.java)
        }
    }

}

