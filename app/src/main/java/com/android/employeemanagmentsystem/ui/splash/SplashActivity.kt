package com.android.employeemanagmentsystem.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.databinding.ActivityLoginBinding
import com.android.employeemanagmentsystem.databinding.ActivitySplashBinding
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.utils.move
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashTime = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSplashTime()
    }

    private fun setSplashTime() {
        GlobalScope.launch {
            delay((1000 * splashTime).toLong())

            withContext(Dispatchers.Main){
                this@SplashActivity.move(LoginActivity::class.java)
            }
        }
    }


}