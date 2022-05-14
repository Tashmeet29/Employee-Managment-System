package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.*
import com.android.employeemanagmentsystem.utils.BASE_URL
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
    @POST("register_user")
    suspend fun employeeRegister(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role_id") role_id: String,
        @Field("org_id") org_id: String,
        @Field("dept_id") dept_id : String,
        @Field("name") name: String,
        @Field("sevarth_id") sevarth_id: String,
        @Field("hint_question") hint_password: String,
        @Field("hint_answer") hint_question: String,
    ): Response<StatusResponse>

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
    //post registration form
//    @FormUrlEncoded
//    @POST("new_registration")
//    suspend fun newRegistration(
//        @Field("sevarth_id") sevarth_id:String,
//        @Field("organization") organization: String,
//        @Field("name") name: String,
//        @Field("department") department: String,
//        @Field("email") email: String,
//        @Field("role") role: String,
//        @Field("password") password: String,
//        @Field("hint_question") hint_question: String,
//        @Field("hint_answer") hint_answer: String,
//
//    ): Response<StatusResponse>


    //Post Add Details Form
    @FormUrlEncoded
    @POST("EmployeeDetails/add_details")
    suspend fun addDetails(
        @Field("first_name") first_name:String,
        @Field("middle_name") middle_name: String,
        @Field("last_name") last_name: String,
        @Field("gender") gender: String,
        @Field("dob") dob: String,
        @Field("sevarth_id") sevarth_id: String,
        @Field("contact_no") contact_no: String,
        @Field("alternative_contact_no") alternative_contact_no: String,
        @Field("qualification") qualification: String,
        @Field("designation") designation: String,
        @Field("experience") experience: String,
        @Field("retirement_date") retirement: String,
        @Field("aadhar_no") aadhar_no: String,
        @Field("pan_no") pan_no: String,
        @Field("cast") cast: String,
        @Field("subcast") subcast: String,
        @Field("blood_grp") blood_grp: String,
        @Field("identification_mark") identification_mark: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("pin_code") pin_code: String,
        @Field("state") state: String,
        @Field("country") country: String,

        ): Response<StatusResponse>


    @FormUrlEncoded
    @POST("edit_details")
    suspend fun editDetails(
        @Field("first_name") first_name:String,
        @Field("middle_name") middle_name: String,
        @Field("last_name") last_name: String,
        @Field("gender") gender: String,
        @Field("dob") dob: String,
        @Field("sevarth_id") sevarth_id: String,
        @Field("contact_no") contact_no: String,
        @Field("alternative_contact_no") alternative_contact_no: String,
        @Field("qualification") qualification: String,
        @Field("designation") designation: String,
        @Field("experience") experience: String,
        @Field("retirement_date") retirement: String,
        @Field("aadhar_no") aadhar_no: String,
        @Field("pan_no") pan_no: String,
        @Field("cast") cast: String,
        @Field("subcast") subcast: String,
        @Field("blood_grp") blood_grp: String,
        @Field("identification_mark") identification_mark: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("pin_code") pin_code: String,
        @Field("state") state: String,
        @Field("country") country: String,

        ): Response<StatusResponse>

    //api request for login of all employees of all roles
    @GET("get_details")
    suspend fun getEmployeeDetails(
        @Query("sevarth_id") sevarth_id: String
    ): Response<EmployeeDetails>

    @GET("show_employees")
    suspend fun getEmployees(): Response<List<Employee>>

    @GET("show_verifications")
    suspend fun getVerifications(): Response<List<Employee>>

    @GET("get_organization")
    suspend fun getOrganizations(): Response<List<Organizations>>

    @GET("get_department")
    suspend fun getDepartments(): Response<List<Department>>

    @GET("get_role")
    suspend fun getRoles(): Response<List<Role>>

    @FormUrlEncoded
    @POST("accept_principle_request")
    suspend fun employeeValidate(
        @Field("employee_id") employee_id: String,
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("decline_principle_request")
    suspend fun employeeInValidate(
        @Field("employee_id") employee_id: String,
    ): Response<StatusResponse>

@FormUrlEncoded
    @POST("check_key")
    suspend fun checkKey(
        @Field("key") key: String,
    ): Response<StatusResponse>


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

