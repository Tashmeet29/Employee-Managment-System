package com.android.employeemanagmentsystem.data.network

import android.util.Log
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.utils.ApiException
import com.bumptech.glide.load.engine.Resource
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

private const val TAG = "SafeApiRequest"
abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString("error"))
                }catch(e: JSONException){ }
            }


            Log.e(
                TAG,
                "\n Failure \n" +
                        "\n code " + response.code()
                        + "\n Body ${response.body()}"
                        + "\n Message Error ${message}"
                        + "\nErrorBody ${response.errorBody().toString()}"
                        + "\n Headers ${response.headers()}"
                        + "\n raw ${response.raw()}"
                        + "\n Message ${response.message()}"
                        + "\n Body $response \n\n "
            )

//            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

}