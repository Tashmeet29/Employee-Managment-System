package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.*
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository : SafeApiRequest() {

    suspend fun empLogin(email: String, password: String, authApi: AuthApi): Employee {
        return apiRequest { authApi.employeeLogin(email, password) }
    }

    suspend fun validateEmail(email: String, authApi: AuthApi): StatusResponse {
        return apiRequest { authApi.emailValidate(email) }
    }

    suspend fun validateAnswer(email: String, answer: String, authApi: AuthApi): StatusResponse {
        return apiRequest { authApi.answerValidate(email, answer) }
    }

    suspend fun changePassword(email: String, password: String, authApi: AuthApi): StatusResponse {
        return apiRequest { authApi.changePassword(email, password) }
    }

    suspend fun newRegistration(
        email: String,
        password: String,
        role_id: String,
        org_id: String,
        dept_id: String,
        name: String,
        sevarth_id: String,
        hint_question: String,
        hint_answer: String,
        authApi: AuthApi
    ): StatusResponse {
        return apiRequest {
            authApi.employeeRegister(
                email,
                password,
                role_id,
                org_id,
                dept_id,
                name,
                sevarth_id,
                hint_question,
                hint_answer
            )
        }
    }

    suspend fun addDetails(
        first_name: String,
        middle_name: String,
        last_name: String,
        gender: String,
        dob: String,
        sevarth_id: String,
        contact_no: String,
        alternative_contact_no: String,
        qualification: String,
        designation: String,
        experience: String,
        retirement_date: String,
        aadhar_no: String,
        pan_no: String,
        cast: String,
        subcast: String,
        blood_grp: String,
        identification_mark: String,
        address: String,
        city: String,
        pin_code: String,
        state: String,
        country: String,
        authApi: AuthApi
    ): StatusResponse {
        return apiRequest {
            authApi.addDetails(
                first_name,
                middle_name,
                last_name,
                gender,
                dob,
                sevarth_id,
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
            )
        }
    }

    suspend fun editDetails(
        first_name: String,
        middle_name: String,
        last_name: String,
        gender: String,
        dob: String,
        sevarth_id: String,
        contact_no: String,
        alternative_contact_no: String,
        qualification: String,
        designation: String,
        experience: String,
        retirement_date: String,
        aadhar_no: String,
        pan_no: String,
        cast: String,
        subcast: String,
        blood_grp: String,
        identification_mark: String,
        address: String,
        city: String,
        pin_code: String,
        state: String,
        country: String,
        authApi: AuthApi
    ): StatusResponse {
        return apiRequest {
            authApi.editDetails(
                first_name,
                middle_name,
                last_name,
                gender,
                dob,
                sevarth_id,
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
            )
        }
    }

    suspend fun saveEmp(employee: Employee, employeeDao: EmployeeDao) =
        withContext(Dispatchers.IO) {
            employeeDao.saveEmployee(employee)
        }

    suspend fun getEmployee(employeeDao: EmployeeDao) =
        withContext(Dispatchers.IO) { employeeDao.getEmployee(0)[0] }


    suspend fun getAllEmployees(authApi: AuthApi): List<Employee> {
        return apiRequest { authApi.getEmployees() }
    }

    suspend fun getAllVerifications(authApi: AuthApi): List<Employee> {
        return apiRequest { authApi.getVerifications() }
    }

    suspend fun getHodVerifications(hod_id:String,authApi: AuthApi): List<Employee> {
        return apiRequest { authApi.getVerifications() }
    }

    suspend fun getOrganizations(authApi: AuthApi): List<Organizations> {
        return apiRequest { authApi.getOrganizations() }
    }

    suspend fun getDepartments(authApi: AuthApi): List<Department> {
        return apiRequest { authApi.getDepartments() }
    }

    suspend fun getRoles(authApi: AuthApi): List<Role> {
        return apiRequest { authApi.getRoles() }
    }

    suspend fun getSplashEmployees(employeeDao: EmployeeDao) = withContext(Dispatchers.IO) {
        employeeDao.getEmployee(0)
    }

    suspend fun logoutUser(employeeDao: EmployeeDao) = withContext(Dispatchers.IO) {
        employeeDao.logOut()
    }

    suspend fun getEmployeeDetailse(sevarth_id: String, authApi: AuthApi) =
        withContext(Dispatchers.IO) {
            apiRequest { authApi.getEmployeeDetails(sevarth_id) }
        }

    suspend fun acceptEmployee(sevarth_id: String, authApi: AuthApi) =
        withContext(Dispatchers.IO) {
            apiRequest { authApi.employeeValidate(sevarth_id) }
        }

    suspend fun declineEmployee(sevarth_id: String, authApi: AuthApi) =
        withContext(Dispatchers.IO) {
            apiRequest { authApi.employeeInValidate(sevarth_id) }
        }
    suspend fun checkKey(key: String, authApi: AuthApi) =
        withContext(Dispatchers.IO) {
            apiRequest { authApi.checkKey(key) }
        }

}