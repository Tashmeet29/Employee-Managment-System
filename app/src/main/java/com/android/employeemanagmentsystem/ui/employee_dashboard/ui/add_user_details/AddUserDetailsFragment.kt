package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.add_user_details

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentAddUserDetailsBinding
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_add_user_details.*
import kotlinx.android.synthetic.main.fragment_user_details.*
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import java.util.*

class AddUserDetailsFragment : Fragment(R.layout.fragment_add_user_details) {

    private lateinit var binding: FragmentAddUserDetailsBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddUserDetailsBinding.bind(view)
        init_variables()
        handleBtnSubmitClick()


    }


    private fun init_variables() {
        authRepository = AuthRepository()
        authApi = AuthApi.invoke()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

    }

    private fun handleBtnSubmitClick() {
        //POST Form on Btn Click
        binding.btnSubmit.setOnClickListener {


            val first_name = binding.etFirstName.text.toString()
            val middle_name = binding.etMiddleName.text.toString()
            val last_name = binding.etLastName.text.toString()
            val gender = (binding.rbMale.isChecked) then "Male" ?: "Female"
            val dob = binding.etDob.text.toString()
            val contact_no = binding.etContNo.text.toString()
            val alternative_contact_no = binding.etAltContNo.text.toString()
            val qualification = binding.etQualification.text.toString()
            val designation = binding.etDesignation.text.toString()
            val experience = binding.etExperience.text.toString()
            val retirement_date = binding.etRetirement.text.toString()
            val aadhar_no = binding.etAadhar.text.toString()
            val pan_no = binding.etPan.text.toString()
            val cast = binding.etCast.text.toString()
            val subcast = binding.etSubcast.text.toString()
            val blood_grp = binding.etBloodGrp.text.toString()
            val identification_mark = binding.etIdMark.text.toString()
            val address = binding.etAddress.text.toString()
            val city = binding.etCity.text.toString()
            val pin_code = binding.etPincode.text.toString()
            val state = binding.etState.text.toString()
            val country = binding.etCountry.text.toString()

//            if (first_name != null||middle_name != null,gender!=) {
//                if () {
            when {
                //checking email and password is empty or not
                first_name.isBlank() -> toast("Please Enter Email")
                middle_name.isBlank() -> toast("Please Enter Email")
                last_name.isBlank() -> toast("Please Enter Email")
                gender.isBlank() -> toast("Please Enter Email")
                dob.isBlank() -> toast("Please Enter Email")
                contact_no.isBlank() -> toast("Please Enter Email")
                alternative_contact_no.isBlank() -> toast("Please Enter Email")
                qualification.isBlank() -> toast("Please Enter Email")
                designation.isBlank() -> toast("Please Enter Email")
                experience.isBlank() -> toast("Please Enter Email")
                retirement_date.isBlank() -> toast("Please Enter Email")
                aadhar_no.isBlank() -> toast("Please Enter Email")
                pan_no.isBlank() -> toast("Please Enter Email")
                cast.isBlank() -> toast("Please Enter Email")
                subcast.isBlank() -> toast("Please Enter Email")
                blood_grp.isBlank() -> toast("Please Enter Email")
                identification_mark.isBlank() -> toast("Please Enter Email")
                address.isBlank() -> toast("Please Enter Email")
                city.isBlank() -> toast("Please Enter Email")
                pin_code.isBlank() -> toast("Please Enter Email")
                state.isBlank() -> toast("Please Enter Email")
                country.isBlank() -> toast("Please Enter Email")

//
                else -> {
                    GlobalScope.launch {
                        val employee = authRepository.getEmployee(employeeDao)

                        try {

                            Log.d(
                                "DAAAATA",
                                "$first_name,\n" +
                                        "$middle_name,\n" +
                                        "$last_name,\n" +
                                        "$gender,\n" +
                                        "$dob,\n" +
                                        "${employee.sevarth_id}\n"+
                                        "$contact_no,\n" +
                                        "$alternative_contact_no,\n" +
                                        "$qualification,\n" +
                                        "$designation,\n" +
                                        "$experience,\n" +
                                        "$retirement_date,\n" +
                                        "$aadhar_no,\n" +
                                        "$pan_no,\n" +
                                        "$cast,\n" +
                                        "$subcast,\n" +
                                        "$blood_grp,\n" +
                                        "$identification_mark,\n" +
                                        "$address,\n" +
                                        "$city,\n" +
                                        "$pin_code,\n" +
                                        "$state,\n" +
                                        "$country,"
                            )
                            //making network call to get user credentials
                            val status: StatusResponse = authRepository.addDetails(
                                first_name,
                                middle_name,
                                last_name,
                                gender,
                                dob,
                                employee.sevarth_id,
                                contact_no,
                                alternative_contact_no,
                                qualification,
                                designation,
                                experience,
                                retirement_date,
                                aadhar_no,
                                pan_no,
                                cast,
                                subcast,
                                blood_grp,
                                identification_mark,
                                address,
                                city,
                                pin_code,
                                state,
                                country,
                                authApi
                            )

                            //role id 1 is for employee
                            if (status.status=="true") {

                                Dispatchers.Main {
                                    toast("Details Added!!")
                                }
                                findNavController().navigate(R.id.nav_user_details)
                            } else {
                                Dispatchers.Main {
                                    toast("Error Occured: ")

                                }
                            }

                        } catch (e: Exception) {

                            Dispatchers.Main {
                                toast("Error Occured: $e")

                            }
                            Log.d("Eror DB", "Error Occured: $e")
                        }

                    }
                }
            }
        }


        binding.etDob.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->

                binding.etDob.setText("$date-${month + 1}-$year")
            }

            DatePickerDialog(
                requireContext(),
                listener,
                year,
                month,
                day
            ).show()

        }
        rb_male.setOnClickListener {
            rb_male.isChecked = true
            rb_female.isChecked = false
        }
        rb_female.setOnClickListener {
            rb_male.isChecked = false
            rb_female.isChecked = true
        }
    }


    fun toast(msg: String) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }


}









