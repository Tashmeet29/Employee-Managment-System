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

