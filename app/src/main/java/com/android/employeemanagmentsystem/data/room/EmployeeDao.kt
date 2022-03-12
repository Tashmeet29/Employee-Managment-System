package com.android.employeemanagmentsystem.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.employeemanagmentsystem.data.models.responses.Employee

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmployee(employee : Employee)

    @Query("SELECT * FROM Employee_Table where UID = :uid")
    fun getEmployee(uid: Int) : List<Employee>

}