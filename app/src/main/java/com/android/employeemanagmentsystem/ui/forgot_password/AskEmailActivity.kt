package com.android.employeemanagmentsystem.ui.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityAskEmailBinding
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class AskEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAskEmailBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAskEmailBinding.inflate(layoutInflater)
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
            val email = binding.etEmail.text.toString()
            toast(email)

            when {
                //checking email and password is empty or not
                email.isBlank() -> toast("Please Enter Email")
                else -> {
                    GlobalScope.launch {

                        try {
                            //making network call to get user credentials
                            val message: StatusResponse = authRepository.validateEmail(email, authApi)
//                            binding.tvAskingEmail.text = message.status.toString()
                            withContext(Dispatchers.Main){
                                Intent(this@AskEmailActivity, ForgotPasswordActivity::class.java).apply{
                                    putExtra("email",email)
                                    putExtra("question",message.status)
                                    startActivity(this)
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