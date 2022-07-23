package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.EmployeeDetails
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentApplyTrainingBinding
import com.android.employeemanagmentsystem.databinding.FragmentHomeBinding
import com.android.employeemanagmentsystem.databinding.FragmentUserDetailsBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_training.TrainingTypesAdapter
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var authApi: AuthApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        authApi = AuthApi.invoke()

        getEmployeeDetails()

    }


    public fun getEmployeeDetails() {
        GlobalScope.launch {

            try {
                val employee = authRepository.getEmployee(employeeDao)

                val employeeDetails: EmployeeDetails =
                    authRepository.getEmployeeDetailse(employee.sevarth_id, authApi)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Welcome to Ems", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Log.d("EXCE","Exception: $e")
                    HomeFragmentDirections.actionNavHomeToAddUserDetailsFragment().apply {
                        findNavController().navigate(this)
                    }
                }

            }

        }
    }
}