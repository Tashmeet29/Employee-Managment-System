package com.android.employeemanagmentsystem.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleBtnSubmitClick()
    }

    private fun handleBtnSubmitClick() {

        //POST Form on Btn Click
        binding.btnSubmit.setOnClickListener {

        }
    }
}