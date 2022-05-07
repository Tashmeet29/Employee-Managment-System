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
import com.android.employeemanagmentsystem.ui.admin_dashboard.AdminDashBoardActivity
import com.android.employeemanagmentsystem.ui.employee_dashboard.EmployeeDashboard
import com.android.employeemanagmentsystem.ui.forgot_password.AskEmailActivity
import com.android.employeemanagmentsystem.ui.forgot_password.ForgotPasswordActivity
import com.android.employeemanagmentsystem.ui.registrar_dashboard.RegistrarDashboard
import com.android.employeemanagmentsystem.ui.registration.RegistrationActivity
import com.android.employeemanagmentsystem.utils.*
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

        handleForgotPasswordClick()

        handleRegisterClick()
    }

    private fun handleRegisterClick() {
        binding.btnRegister.setOnClickListener {
            this@LoginActivity.move(RegistrationActivity::class.java, false)
        }
    }

    private fun handleForgotPasswordClick() {
        binding.tvForgotPassword.setOnClickListener {
            this@LoginActivity.move(AskEmailActivity::class.java, true)
        }
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

                            val employeeRoleId = employee.role_id.toInt()

                            //role id 1 is for employee
                            when {
                                employeeRoleId.toInt() == ROLE_EMPLOYEE -> {
                                    this@LoginActivity.move(EmployeeDashboard::class.java, true)
                                }
                                employeeRoleId.toInt() == ROLE_Registrar -> {
                                    this@LoginActivity.move(RegistrarDashboard::class.java, true)
                                }
                                else -> {
                                    this@LoginActivity.move(AdminDashBoardActivity::class.java, true)

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