package com.android.employeemanagmentsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.databinding.ActivityLoginBinding
import com.android.employeemanagmentsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}