package com.android.employeemanagmentsystem.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

fun Uri.getOriginalFileName(context: Context): String? {
    return context.contentResolver.query(this, null, null, null, null)?.use {
        val nameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameColumnIndex)
    }
}

fun Int.getTrainingStatusById(): String{

    val trainingStatus = this

    return when(trainingStatus){
        1 -> "Applied"
        2 -> "Approved by HOD"
        3 -> "Approved by Principal"
        4 -> "Decline by HOD"
        5 -> "Decline by Principal"

        else -> "Unknown status id found"
    }


}


