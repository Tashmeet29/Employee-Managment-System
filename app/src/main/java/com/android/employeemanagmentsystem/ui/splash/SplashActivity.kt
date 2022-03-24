package com.android.employeemanagmentsystem.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityLoginBinding
import com.android.employeemanagmentsystem.databinding.ActivitySplashBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.AdminDashBoardActivity
import com.android.employeemanagmentsystem.ui.employee_dashboard.EmployeeDashboard
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.utils.move
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashTime = 4
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(applicationContext).getEmployeeDao()

        setSplashTime()
    }

    //Delay the screen for some seconds
    private fun setSplashTime() {
        GlobalScope.launch {
            //delay the time
            delay((1000 * splashTime).toLong())

            //get employee from database
            val savedEmployees = authRepository.getSplashEmployees(employeeDao)

            handleNavigation(savedEmployees)


        }
    }

    //handles the navigation after getting employee from room database
    private suspend fun handleNavigation(savedEmployees: List<Employee>) {
        //employee is not present in database
        if (savedEmployees.isEmpty()) {

            //navigate user to login screen
            withContext(Dispatchers.Main) {
                this@SplashActivity.move(LoginActivity::class.java)
            }
        } else {
            //todo:  navigation employee according to role id

            val employeeRoleId = savedEmployees[0].role_id

            //role id 1 is for employee
            if (employeeRoleId.toInt() == 1){
                this@SplashActivity.move(EmployeeDashboard::class.java, true)
            }else{
                this@SplashActivity.move(AdminDashBoardActivity::class.java, true)

            }

        }
    }


}