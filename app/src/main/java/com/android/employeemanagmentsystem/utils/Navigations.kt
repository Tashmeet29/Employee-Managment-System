package com.android.employeemanagmentsystem.utils

import android.app.Activity
import android.content.Intent

fun Activity.move(destination: Class<*>,addFlags: Boolean = false){
    val intent = Intent(this, destination)
    if (addFlags) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        this.finish()
    }
    this.startActivity(intent)
}