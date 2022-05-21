package com.android.employeemanagmentsystem.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log

fun Uri.getOriginalFileName(context: Context): String? {
    return context.contentResolver.query(this, null, null, null, null)?.use {
        val nameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameColumnIndex)
    }
}

fun Int.getTrainingStatusById(): String{

    return when(this){
        TRAINING_APPLIED_TO_HOD -> "Applied to HOD"
        TRAINING_APPLIED_TO_PRINCIPLE -> "Applied to Principal"
        TRAINING_APPROVED_BY_HOD -> "Approved by HOD"
        TRAINING_DECLINE_BY_HOD -> "Decline by HOD"
        TRAINING_APPROVED_BY_PRINCIPAL -> "Approved by Principal"
        TRAINING_DECLINED_BY_PRINCIPLE -> "Decline by Principal"
        TRAINING_COMPLETED -> "Completed"
        -1 -> "Applied By Principal"

        else -> "Unknown status id found"
    }


}

fun Int.getStringDepartment(): String{
    return when(this){
        1 -> "CS"
        2 -> "IT"
        3 -> "ME"
        4 -> "CE"
        5 -> "EXTC"
        else -> "$this"
    }
}

fun Int.getIoApplicationStatusById(): String{

    return when(this){
        IO_APPLICATION_APPLIED -> "Applied"
        IO_APPROVED_BY_HOD -> "Approved By HOD"
        IO_APPROVED_BY_REGISTRAR -> "Approved By Registrar"
        IO_APPROVED_BY_PRINCIPLE -> "Approved By Principle"
        IO_Declined_BY_Hod -> "Decline by HOD"
        IO_Declined_BY_Registrar -> "Decline by Registrar"
        IO_Declined_BY_Principle -> "Decline By Principal"
        IO_APPLIED_BY_HOD -> "Applied By HOD"
        IO_APPLIED_BY_Principle -> "Applied By Principal"
        IO_APPLIED_BY_REGISTRAR -> "Applied By Registrar"


        else -> "Unknown status id found"
    }


}



fun String.getDurationInWeeks(): String {
    try {
        val day: Int = this.toInt()

        return if(day < 7) "$this days "
        else{
            val week = (day % 365) / 7
            "$week weeks"
        }
    }catch (e: Exception){
        Log.e(TAG, "getDurationInWeeks: can not be converted $this", )
    }

    return this

}
