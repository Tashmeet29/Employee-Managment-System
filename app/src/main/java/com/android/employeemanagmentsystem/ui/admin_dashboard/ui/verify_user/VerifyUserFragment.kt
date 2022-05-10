package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.verify_user

import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityLoginBinding
import com.android.employeemanagmentsystem.databinding.FragmentVerifyUserBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.AdminDashBoardActivity
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications.AppliedTrainingsAdapter
import com.android.employeemanagmentsystem.ui.employee_dashboard.EmployeeDashboard
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.home.HomeFragmentDirections
import com.android.employeemanagmentsystem.ui.registrar_dashboard.RegistrarDashboard
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class VerifyUserFragment : Fragment(R.layout.fragment_verify_user),
    EmployeeVerificationsAdapter.onEmployeeClickListener {
    private lateinit var binding: FragmentVerifyUserBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao


    lateinit var employees: List<Employee>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentVerifyUserBinding.bind(view)

        authRepository = AuthRepository()
        authApi = AuthApi.invoke()

        getEmployees()
    }

    private fun getEmployees() {
        val authRepository = AuthRepository()
        val authApi = AuthApi.invoke()

        GlobalScope.launch {

            employees = authRepository.getAllVerifications(authApi)

            withContext(Dispatchers.Main) {
                binding.recyclerView.apply {
                    adapter = EmployeeVerificationsAdapter(employees, this@VerifyUserFragment)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

    override fun onAcceptBtnClick(employee: Employee) {

        when {
            else -> {
                GlobalScope.launch {
                    try {
                        //making network call to get user credentials
                        val employee: StatusResponse =
                            authRepository.acceptEmployee(employee.sevarth_id, authApi)

                        //saving the employee in local database
                        if (employee.status == "true") {
                            withContext(Dispatchers.Main) {
                                getEmployees()
                                Toast.makeText(context, "Application Accepted!", Toast.LENGTH_SHORT).show()
                            }


                        } else {
                            Dispatchers.Main {
                                Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show()
                                getEmployees()
                            }
                        }


                    } catch (e: Exception) {
                        Toast.makeText(context, "Error $e", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }


    override fun onDeclineBtnClick(employee: Employee) {
        when {
            else -> {
                GlobalScope.launch {
                    try {
                        //making network call to get user credentials
                        val employee: StatusResponse = authRepository.declineEmployee(employee.sevarth_id,authApi)
                        //saving the employee in local database
                        if (employee.status == "true") {
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, "Application Decline Success!", Toast.LENGTH_SHORT).show()
                                getEmployees()
                            }
                        } else {
                            Dispatchers.Main {
                                Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show()
                                getEmployees()
                            }
                        }

                    } catch (e: Exception) {
                        Toast.makeText(context, "Error $e", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

}

