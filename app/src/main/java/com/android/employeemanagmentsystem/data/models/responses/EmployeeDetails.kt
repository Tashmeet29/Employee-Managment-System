package com.android.employeemanagmentsystem.data.models.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

data class EmployeeDetails(
    val sevarth_id: String,
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val dob: String,
    val qualification: String,
    val department_id: String,
    val cast: String,
    val subcast: String,
    val designation: String,
    val retriement_date: String,
    val experience: String,
    val aadhar_no: String,
    val pan_no: String,
    val blood_grp: String,
    val identification_mark: String,
    val photo: String,
    val contact_no: String,
    val alternative_contact_no: String,
    val address: String,
    val city: String,
    val pin_code: String,
    val state: String,
    val country: String,
    val gender: String

)

