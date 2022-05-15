package com.android.employeemanagmentsystem.ui.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityCheckKeyBinding
import com.android.employeemanagmentsystem.ui.forgot_password.ForgotPasswordActivity
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*

class CheckKeyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckKeyBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckKeyBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init_variables()
        handleSubmitButtonClick()

    }

    private fun init_variables() {
        authRepository = AuthRepository()
        authApi = AuthApi.invoke()
//        employeeDao = AppDatabase.invoke(applicationContext).getEmployeeDao()
    }
    //handles click of login button
    @DelicateCoroutinesApi
    private fun handleSubmitButtonClick() {
        //handles the click of login button
        binding.btnSubmit.setOnClickListener {
            //getting email and password after click of login button
            val key = binding.etKey.text.toString()
            toast(key)

            when {
                //checking email and password is empty or not
                key.isBlank() -> toast("Please Enter Key")
                else -> {
                    GlobalScope.launch {

                        try {
                            //making network call to get user credentials
                            val message: StatusResponse = authRepository.checkKey(key, authApi)
//                            binding.tvAskingEmail.text = message.status.toString()
                            withContext(Dispatchers.Main){
                                toast("Authorized Successfully")
                                if(message.status == "true") {
                                    Intent(
                                        this@CheckKeyActivity,
                                        RegistrationActivity::class.java
                                    ).apply {

                                        startActivity(this)
                                    }
                                }else{
                                    toast("Invalid App Key!!")
                                }
                            }

                        }catch (e: Exception){
                            handleException(e)
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this@CheckKeyActivity, LoginActivity::class.java))
        finish()
    }
}