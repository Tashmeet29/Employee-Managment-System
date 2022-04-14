package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_applications

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.network.apis.IOApplicationApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.IOApplicationRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentAppliedApplicationsBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_trainings.AppliedTrainingFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AppliedIoApplicationsFr"
class AppliedIoApplicationsFragment: Fragment(R.layout.fragment_applied_applications), AppliedIoApplicationsAdapter.ApplicationClickListener {

    private lateinit var binding: FragmentAppliedApplicationsBinding
    private lateinit var ioApplicationRepository: IOApplicationRepository
    private lateinit var ioApplicationApi: IOApplicationApi
    private lateinit var employeeDao: EmployeeDao
    private lateinit var authRepository: AuthRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAppliedApplicationsBinding.bind(view)

        ioApplicationApi = IOApplicationApi.invoke()
        ioApplicationRepository = IOApplicationRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        authRepository = AuthRepository()

        getApplications()
    }

    private fun getApplications(){

        GlobalScope.launch {
            val sevarthId = authRepository.getEmployee(employeeDao).sevarth_id
            val applications = ioApplicationRepository.getAppliedApplications(sevarthId, ioApplicationApi)

            withContext(Dispatchers.Main) {
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = AppliedIoApplicationsAdapter(
                        applications,
                        this@AppliedIoApplicationsFragment
                    )
                }
            }

        }

    }

    override fun onApplicationItemClicked(application: Application) {
        AppliedIoApplicationsFragmentDirections.actionNavAppliedApplicationsToIoApplicationDetails(
            application
        ).apply {
            findNavController().navigate(this)
        }
    }
}