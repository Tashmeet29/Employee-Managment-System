package com.android.employeemanagmentsystem.ui.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityForgotPasswordBinding
import com.android.employeemanagmentsystem.databinding.ActivityResetPasswordBinding
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding:ActivityResetPasswordBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init_variables()
        var email:String = intent.getStringExtra("email").toString()
        handleSubmitButtonClick(email)
    }
    private fun init_variables() {
        authRepository = AuthRepository()
        authApi = AuthApi.invoke()
    }
    //handles click of login button
    @DelicateCoroutinesApi
    private fun handleSubmitButtonClick(email:String) {
        //handles the click of login button
        binding.btnSubmit.setOnClickListener {
            //getting email and password after click of login button
            val newPass = binding.etNewPass.text.toString()
            toast(email)

            when {
                //checking email and password is empty or not
                newPass.isBlank() -> toast("Please Enter Email")
                else -> {
                    GlobalScope.launch {

                        try {
                            //making network call to get user credentials
                            val message: StatusResponse = authRepository.changePassword(email,newPass, authApi)
//                            binding.tvAskingEmail.text = message.status.toString()
                            if(message.status=="true"){
                                withContext(Dispatchers.Main){
                                    toast(message.status)
                                    Intent(this@ResetPasswordActivity, LoginActivity::class.java).apply{
                                        putExtra("email",email)
                                        startActivity(this)
                                    }
                                }
                            }else{
                                withContext(Dispatchers.Main){
                                    toast(message.status)
//                                    Intent(this@ResetPasswordActivity, LoginActivity::class.java).apply{
//                                        putExtra("email",email)
//                                        startActivity(this)
//                                    }
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
}