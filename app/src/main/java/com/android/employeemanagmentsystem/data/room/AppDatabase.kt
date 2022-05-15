package com.android.employeemanagmentsystem.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.employeemanagmentsystem.data.models.responses.Employee

@Database(
    entities = [Employee::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}