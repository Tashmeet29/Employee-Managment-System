package com.android.employeemanagmentsystem.data.network.apis

import com.android.employeemanagmentsystem.data.models.responses.Product
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.utils.BASE_URL
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface DsrApi {

    @GET("Dsr/getCsProducts")
    suspend fun getCsProduct(
    ) : Response<List<Product>>

    @Multipart
    @POST("Dsr/add_CsProducts")
    suspend fun addCsProduct(
        @Part("Organization_ID") Organization_ID: RequestBody,
        @Part("DSR_no") DSR_no: RequestBody,
        @Part("purchase_date") purchase_date: RequestBody,
        @Part("supplier_name") supplier_name: RequestBody,
        @Part("Supplier_Address") Supplier_Address: RequestBody,
        @Part("product_name") product_name: RequestBody,
        @Part("product_desc") product_desc: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("Price_Per_Quantity") Price_Per_Quantity: RequestBody,
        @Part("Quantity_Distributed") Quantity_Distributed: RequestBody,
        @Part("remarks") remarks: RequestBody,
    ): Response<List<Product>>

    @GET("Dsr/getLibProducts")
    suspend fun getLibProduct(
    ) : Response<List<ProductDistr>>

    @GET("Dsr/getOfficeProducts")
    suspend fun getOfficeProduct() : Response<List<ProductDistr>>

    //api request for login of all employees of all roles
    @FormUrlEncoded
    @POST("Dsr/getDeptProducts")
    suspend fun getDeptProduct(
        @Field("dept") dept: Int,
    ): Response<List<ProductDistr>>

    @FormUrlEncoded
    @POST("Dsr/getHostProducts")
    suspend fun getHostProduct(
        @Field("hostel") hostel: Int,
    ) : Response<List<ProductDistr>>

    companion object{
        operator fun invoke(
            //networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : DsrApi {

//            val okkHttpclient = OkHttpClient.Builder()
//                .addInterceptor(networkConnectionInterceptor)
//                .build()

            return Retrofit.Builder()
                //.client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DsrApi::class.java)
        }
    }
}

