package com.android.employeemanagmentsystem.data.models.responses

import android.os.Parcelable
import com.android.employeemanagmentsystem.utils.APPLICATION_TYPE_INWARD
import com.android.employeemanagmentsystem.utils.getIoApplicationStatusById
import com.android.employeemanagmentsystem.utils.getTrainingStatusById
import com.android.employeemanagmentsystem.utils.then
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Application(
    val id: String,
    val sevarth_id: String,
    val date: String,
    val remark: String,
    val application: String,
    val title: String,
    val hod_id: String,
    val registrar_id: String,
    val principal_id: String,
    val status_id: String,
    val application_type: String
) : Parcelable {

    val getApplicationStringType
        get() = (application_type.toInt() == APPLICATION_TYPE_INWARD) then "Inward Application"
            ?: "Outward Application"

    val getApplicationStringStatus get() = status_id.toInt().getIoApplicationStatusById()

    override fun toString(): String {
        return """
             id: ${id},
             sevarth_id: ${sevarth_id},
             date: ${date},
             remark: ${remark},
             application: ${application},
             title: ${title},
             hod_id: ${hod_id},
             registrar_id: ${registrar_id},
             principal_id: ${principal_id},
             status_id: ${status_id},
             application_type ${application_type}
        
        """
    }
}
