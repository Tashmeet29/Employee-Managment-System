package com.android.employeemanagmentsystem.ui.logout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentLogoutBinding
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.utils.move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogoutFragment: Fragment(R.layout.fragment_logout) {

    private lateinit var binding: FragmentLogoutBinding
    private lateinit var employeeDao: EmployeeDao
    private lateinit var authRepository: AuthRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLogoutBinding.bind(view)

        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        authRepository = AuthRepository()

        logout()
    }

    private fun logout() {
        GlobalScope.launch {
            authRepository.logoutUser(employeeDao)

            withContext(Dispatchers.Main){
                activity?.move(LoginActivity::class.java, true)
            }

        }
    }
}