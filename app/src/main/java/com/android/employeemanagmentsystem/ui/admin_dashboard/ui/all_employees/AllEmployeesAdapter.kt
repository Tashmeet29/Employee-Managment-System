package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.all_employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.databinding.ItemEmployeeBinding

class AllEmployeesAdapter(
    private val employees: List<Employee>,
    private val listener: EmployeeClickListener
): RecyclerView.Adapter<AllEmployeesAdapter.EmployeeViewHolder>() {

    interface EmployeeClickListener{
        fun onTrainingItemClicked(employee: Employee)
    }

    inner class EmployeeViewHolder(val binding: ItemEmployeeBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onTrainingItemClicked(employees[adapterPosition])
                }
            }
        }

        fun bindDate(employee: Employee){
            binding.apply {
                tvSevarthid.text = employee.sevarth_id
                tvName.text = employee.name
                tvEmail.text = employee.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllEmployeesAdapter.EmployeeViewHolder, position: Int) {
        holder.bindDate(
            employees[position]
        )
    }

    override fun getItemCount() = employees.size


}