package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.AuthApi
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository : SafeApiRequest() {

    suspend fun empLogin(email: String, password: String, authApi: AuthApi): Employee {
        return apiRequest { authApi.employeeLogin(email, password) }
    }

    suspend fun saveEmp(employee: Employee, employeeDao: EmployeeDao) =
        withContext(Dispatchers.IO) {
            employeeDao.saveEmployee(employee)
        }

    suspend fun getEmployee(employeeDao: EmployeeDao) =
        withContext(Dispatchers.IO) { employeeDao.getEmployee(0)[0] }

    suspend fun getSplashEmployees(employeeDao: EmployeeDao) = withContext(Dispatchers.IO) {
        employeeDao.getEmployee(0)
    }

    suspend fun logoutUser(employeeDao: EmployeeDao) = withContext(Dispatchers.IO){
        employeeDao.logOut()
    }

    suspend fun getEmployeeDetailse(sevarth_id: String, authApi: AuthApi) = withContext(Dispatchers.IO){
        apiRequest { authApi.getEmployeeDetails(sevarth_id) }
    }

}