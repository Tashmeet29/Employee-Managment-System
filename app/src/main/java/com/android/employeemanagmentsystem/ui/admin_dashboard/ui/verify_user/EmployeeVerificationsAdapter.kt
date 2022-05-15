package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.verify_user


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.databinding.ItemTrainingsBinding
import com.android.employeemanagmentsystem.databinding.ItemVerifyEmployeeBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.all_employees.AllEmployeesAdapter
import com.android.employeemanagmentsystem.utils.getDurationInWeeks
import com.android.employeemanagmentsystem.utils.getTrainingStatusById

class EmployeeVerificationsAdapter(
    private val employees: List<Employee>,
    private val listener: EmployeeVerificationsAdapter.onEmployeeClickListener
): RecyclerView.Adapter<EmployeeVerificationsAdapter.EmployeeViewHolder>() {

    interface onEmployeeClickListener{
        fun onAcceptBtnClick(employee: Employee)
        fun onDeclineBtnClick(employee: Employee)
    }

    inner class EmployeeViewHolder(val binding: ItemVerifyEmployeeBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.btnAccept.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onAcceptBtnClick(employees[adapterPosition])
                }
            }
            binding.btnDecline.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onDeclineBtnClick(employees[adapterPosition])
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
        val binding = ItemVerifyEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeVerificationsAdapter.EmployeeViewHolder, position: Int) {
        holder.bindDate(
            employees[position]
        )
    }

    override fun getItemCount() = employees.size


}