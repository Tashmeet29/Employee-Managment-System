package com.android.employeemanagmentsystem.data.models.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employee_Table")
data class Employee(
    val sevarth_id: String,
    val org_id: String,
    val dept_id: String,
    val role_id: Integer,
    val email: String,
    val password: String,
    val name: String,

    @PrimaryKey
    val UID: Int = 0

)
