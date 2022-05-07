package com.android.employeemanagmentsystem.ui.forgot_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityAskEmailBinding
import com.android.employeemanagmentsystem.databinding.ActivityForgotPasswordBinding
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.coroutines.*

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init_variables()

        var question = intent.getStringExtra("question")
        var email:String = intent.getStringExtra("email").toString()

        handleSubmitButtonClick(email)


        binding.apply {
            tv_forgot_pass_que.text = question

        }
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
            val answer = binding.etAnswer.text.toString()
            toast(answer)

            when {
                //checking email and password is empty or not
                answer.isBlank() -> toast("Please Enter Email")
                else -> {
                    GlobalScope.launch {

                        try {
                            //making network call to get user credentials
                            val message: StatusResponse = authRepository.validateAnswer(email,answer, authApi)
//                            binding.tvAskingEmail.text = message.status.toString()
                            if(message.status=="true"){
                                withContext(Dispatchers.Main){
                                    Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java).apply{
                                        putExtra("email",email)
                                        startActivity(this)
                                    }
                                }
                            }else{

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