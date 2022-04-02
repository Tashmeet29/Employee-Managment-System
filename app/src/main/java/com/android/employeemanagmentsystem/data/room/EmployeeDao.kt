package com.android.employeemanagmentsystem.data.room

import androidx.room.*
import com.android.employeemanagmentsystem.data.models.responses.Employee

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmployee(employee : Employee)

    @Query("SELECT * FROM Employee_Table where UID = :uid")
    fun getEmployee(uid: Int) : List<Employee>


    @Query("DELETE  FROM Employee_Table Where Uid = :id")
    fun logOut(id: Int = 0)

}