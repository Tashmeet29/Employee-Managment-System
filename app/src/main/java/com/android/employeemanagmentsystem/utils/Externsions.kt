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

    return when(this){
        1 -> "Applied to HOD"
        2 -> "Applied to Principle"
        3 -> "Approved by HOD"
        4 -> "Decline by HOD"
        5 -> "Approved by Principle"
        6 -> "Decline by Principle"
        7 -> "Completed"

        else -> "Unknown status id found"
    }


}

fun String.getDurationInWeeks(): String {
    val day: Int = this.toInt()

    return if(day < 7) "$this days "
    else{
        val week = (day % 365) / 7
        "$week weeks"
    }
}
