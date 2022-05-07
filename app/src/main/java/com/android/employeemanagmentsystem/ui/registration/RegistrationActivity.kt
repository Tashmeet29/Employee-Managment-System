package com.android.employeemanagmentsystem.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.databinding.ActivityRegistrarBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegistrarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}