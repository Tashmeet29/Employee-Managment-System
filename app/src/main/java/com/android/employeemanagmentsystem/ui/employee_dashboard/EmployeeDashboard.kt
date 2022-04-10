package com.android.employeemanagmentsystem.ui.employee_dashboard

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityEmployeeDashboardBinding
import com.android.employeemanagmentsystem.utils.changeStatusBarColor

class EmployeeDashboard : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityEmployeeDashboardBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmployeeDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.changeStatusBarColor()
        setSupportActionBar(binding.appBarEmployeeDashboard.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_employee_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_employee_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}