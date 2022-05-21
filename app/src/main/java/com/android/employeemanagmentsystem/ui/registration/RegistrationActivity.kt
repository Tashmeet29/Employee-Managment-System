package com.android.employeemanagmentsystem.ui.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityRegistrationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.view.View
import com.android.employeemanagmentsystem.data.models.responses.*
import com.android.employeemanagmentsystem.ui.login.LoginActivity
import com.android.employeemanagmentsystem.utils.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var authApi: AuthApi
    private lateinit var employeeDao: EmployeeDao
    private var selectedRole = "-1"
    private var selectedOrg = "-1"
    private var selectedDep = "-1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init_variables()
        getOrganization()
        getDepartments()
        getRoles()
        handleBtnSubmitClick()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //Go Back to Login
        startActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
        finish()

    }
    private fun init_variables() {
        authRepository = AuthRepository()
        authApi = AuthApi.invoke()
    }


    private fun handleBtnSubmitClick() {
        //POST Form on Btn Click
        binding.btnSubmit.setOnClickListener {
           val email = binding.etEmail.text.toString()
           val password = binding.etPassword.text.toString()
           val role_id = selectedRole
           val org_id = selectedOrg
           val dep_id = selectedDep
           val name = binding.etEmpName.text.toString()
           val sevarth_id = binding.etSevarthId.text.toString()
           val hint_question = binding.etHintQuestion.text.toString()
           val hint_answer = binding.etAnswer.text.toString()

            when {
                //checking email and password is empty or not
                email.isBlank() -> toast("Please Enter Email")
                password.isBlank() -> toast("Please Enter Password")
                role_id.isBlank()->toast("Please Select Role")
                dep_id.isBlank()->toast("Please Select Department")
                org_id.isBlank()->toast("Please Select Organisation")
                name.isBlank()->toast("Please Enter Name")
                sevarth_id.isBlank()->toast("Please enter Sevarth Id")
                sevarth_id.length != 12 -> toast("Please Enter 12 digits sevarth id")
                hint_answer.isBlank()->toast("Please enter Hint Answer")
                hint_question.isBlank()->toast("Please enter Question")

                else -> {
                    GlobalScope.launch {

                        try {
                            //making network call to get user credentials
                            val status: StatusResponse = authRepository.newRegistration(email, password,role_id,org_id,dep_id,name,sevarth_id,hint_question,hint_answer, authApi)

                            //role id 1 is for employee

                            this@RegistrationActivity.move(LoginActivity::class.java, true)

                        }catch (e: Exception){
                            handleException(e)
                        }

                    }
                }
            }
        }


    }


    public fun getOrganization() {
        GlobalScope.launch {
            val types: List<Organizations> = authRepository.getOrganizations(authApi)

            val adapter = OrganizationsAdapter(this@RegistrationActivity, types)

            withContext(Dispatchers.Main) {
                binding.spinnerOrganization.adapter = adapter

                binding.spinnerOrganization.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selectedOrg = types[p2].org_id
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }

            }
        }
    }
    public fun getDepartments() {
        GlobalScope.launch {
            val departments: List<Department> = authRepository.getDepartments(authApi)

            val adapter = DepartmentsAdapter(this@RegistrationActivity, departments)

            withContext(Dispatchers.Main) {
                binding.spinnerDepartments.adapter = adapter

                binding.spinnerDepartments.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selectedDep = departments[p2].dept_id
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }

            }
        }
    }
    public fun getRoles() {
        GlobalScope.launch {
            val types: List<Role> = authRepository.getRoles(authApi)

            val adapter = RolesAdapter(this@RegistrationActivity, types)

            withContext(Dispatchers.Main) {
                binding.spinnerRole.adapter = adapter

                binding.spinnerRole.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selectedRole = types[p2].role_id
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }

            }
        }
    }
}