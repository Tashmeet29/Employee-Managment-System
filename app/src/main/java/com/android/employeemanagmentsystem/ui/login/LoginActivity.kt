package com.android.employeemanagmentsystem.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityLoginBinding
import com.android.employeemanagmentsystem.utils.ApiException
import com.android.employeemanagmentsystem.utils.NoInternetException
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*

private const val TAG = "xLoginActivity"
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init_variables()

        handleLoginButtonClick()
    }

    private fun init_variables() {
        authRepository = AuthRepository()
        authApi = AuthApi.invoke()
        employeeDao = AppDatabase.invoke(applicationContext).getEmployeeDao()
    }

    //handles click of login button
    @DelicateCoroutinesApi
    private fun handleLoginButtonClick() {
        //handles the click of login button
        binding.btnSubmit.setOnClickListener {
            //getting email and password after click of login button
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            when {
                //checking email and password is empty or not
                email.isBlank() -> toast("Please Enter Email")
                password.isBlank() -> toast("Please Enter Password")
                else -> {
                    GlobalScope.launch {
                        try {
                            //making network call to get user credentials
                            val employee: Employee = authRepository.empLogin(email, password, authApi)

                            //saving the employee in local database
                            authRepository.saveEmp(employee, employeeDao)

                            //todo navigate employee by user id


                        }catch (e: Exception){
                            handleException(e)
                        }

                    }
                }
            }
        }
    }
}