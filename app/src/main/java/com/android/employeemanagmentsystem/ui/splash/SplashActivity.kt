package com.android.employeemanagmentsystem.ui.splash

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivitySplashBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.AdminDashBoardActivity
import com.android.employeemanagmentsystem.ui.employee_dashboard.EmployeeDashboard
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.ui.registrar_dashboard.RegistrarDashboard
import com.android.employeemanagmentsystem.utils.ROLE_EMPLOYEE
import com.android.employeemanagmentsystem.utils.ROLE_Registrar
import com.android.employeemanagmentsystem.utils.move
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*
import java.util.jar.Manifest

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashTime = 1
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(applicationContext).getEmployeeDao()

        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ){
          ActivityCompat.requestPermissions(this,
              arrayOf(
                  android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                  android.Manifest.permission.READ_EXTERNAL_STORAGE,
                  android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
              ),
              100
          )
        }else{
            setSplashTime()
        }
    }

    //Delay the screen for some seconds
    private fun setSplashTime() {
        GlobalScope.launch {
            //delay the time
            delay((1000 * splashTime).toLong())

            //get employee from database
            val savedEmployees = authRepository.getSplashEmployees(employeeDao)

            handleNavigation(savedEmployees)
            finish()


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

            val employeeRoleId = savedEmployees[0].role_id

            //role id 1 is for employee
            when {
                employeeRoleId.toInt() == ROLE_EMPLOYEE -> {
                    this@SplashActivity.move(EmployeeDashboard::class.java, true)
                }
                employeeRoleId.toInt() == ROLE_Registrar -> {
                    this@SplashActivity.move(RegistrarDashboard::class.java, true)
                }
                else -> {
                    this@SplashActivity.move(AdminDashBoardActivity::class.java, true)

                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setSplashTime()
            }else{
                toast("You need to accept Store Permission to Proceed")
                checkStoragePermission()
            }

        }
    }
}