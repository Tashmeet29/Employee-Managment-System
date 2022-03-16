package com.android.employeemanagmentsystem.data.models.responses

data class Training(
    val id: String,
    val sevarth_id: String,
    val name: String,
    val duration: String,
    val start_date: String,
    val end_date: String,
    val org_name: String,
    val organized_by: String,
    val apply_letter: String,
    val comp_certificate: String,
    val training_status_id: Int,
    val hod_id: String,
    val principal_id: String
)

