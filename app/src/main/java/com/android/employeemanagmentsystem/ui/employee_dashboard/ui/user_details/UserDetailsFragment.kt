package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.user_details

import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.home.HomeFragmentDirections
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var authApi: AuthApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserDetailsBinding.bind(view)
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
                    binding.apply {
                        tvName.text = employeeDetails.first_name+" "+employeeDetails.middle_name+" "+employeeDetails.last_name
                        tvSevarthid.text = employeeDetails.sevarth_id
                        tvAadharNum.text = employeeDetails.aadhar_no
                        tvPanNum.text = employeeDetails.pan_no
                        tvContact.text = employeeDetails.contact_no
                        tvAddress.text = employeeDetails.address
                        tvDob.text = employeeDetails.dob
                        tvDesignation.text = employeeDetails.designation
                        tvBloodgroup.text = employeeDetails.blood_grp
                        tvCast.text = employeeDetails.cast
                        tvRetirement.text = employeeDetails.retriement_date
                        tvAlternateContact.text = employeeDetails.alternative_contact_no
                        tvCity.text = employeeDetails.city
                        tvState.text = employeeDetails.state
                        tvPincode.text = employeeDetails.pin_code
                        tvCountry.text = employeeDetails.country
                        tvQualification.text = employeeDetails.qualification
                        tvExperience.text = employeeDetails.experience
                        tvRetirement.text = employeeDetails.retriement_date
                        tvIdentificationmark.text = employeeDetails.identification_mark
                        tvGender.text = employeeDetails.gender

                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    HomeFragmentDirections.actionNavHomeToAddUserDetailsFragment().apply {
                        findNavController().navigate(this)
                    }
                }

            }

        }
    }
}