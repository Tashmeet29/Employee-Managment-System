package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.all_employees

import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.all_employees.AllEmployeesAdapter
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
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.databinding.FragmentAllEmployeesBinding
import com.android.employeemanagmentsystem.databinding.FragmentVerifyUserBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications.AppliedTrainingsAdapter
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.verify_user.EmployeeVerificationsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AllEmployeesFragment : Fragment(R.layout.fragment_all_employees),
    AllEmployeesAdapter.EmployeeClickListener {
    private lateinit var binding: FragmentAllEmployeesBinding
    lateinit var employees: List<Employee>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAllEmployeesBinding.bind(view)
        getEmployees()
    }

    private fun getEmployees() {
        val authRepository = AuthRepository()
        val authApi = AuthApi.invoke()

        GlobalScope.launch {

            employees = authRepository.getAllEmployees(authApi)

            withContext(Dispatchers.Main) {

                binding.recyclerView.apply {
                    adapter = AllEmployeesAdapter(employees, this@AllEmployeesFragment)
                    layoutManager = LinearLayoutManager(requireContext())
                }


            }

        }


    }

    override fun onTrainingItemClicked(employee: Employee) {
        TODO("Not yet implemented")
    }

}

