package com.android.employeemanagmentsystem.ui.forgot_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityAskEmailBinding
import com.android.employeemanagmentsystem.databinding.ActivityLoginBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.AdminDashBoardActivity
import com.android.employeemanagmentsystem.ui.employee_dashboard.EmployeeDashboard
import com.android.employeemanagmentsystem.ui.registrar_dashboard.RegistrarDashboard
import com.android.employeemanagmentsystem.ui.registration.RegistrationActivity
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AskEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAskEmailBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAskEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    //handles click of login button
    @DelicateCoroutinesApi
    private fun handleLoginButtonClick() {
        //handles the click of login button
        binding.btnSubmit.setOnClickListener {
            //getting email and password after click of login button
            val email = binding.etEmail.text.toString()

            when {
                //checking email and password is empty or not
                email.isBlank() -> toast("Please Enter Email")
                else -> {
                    GlobalScope.launch {

                        try {
                            //making network call to get user credentials
                            val message: String = authRepository.validateEmail(email, authApi)
                            binding.tvAskingEmail.text = message

                        }catch (e: Exception){
                            handleException(e)
                        }
                    }
                }
            }
        }
    }
}