package com.android.employeemanagmentsystem.data.network

import com.android.employeemanagmentsystem.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MyApi {



    companion object{
        operator fun invoke(
            //networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi {

//            val okkHttpclient = OkHttpClient.Builder()
//                .addInterceptor(networkConnectionInterceptor)
//                .build()

            return Retrofit.Builder()
                //.client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

