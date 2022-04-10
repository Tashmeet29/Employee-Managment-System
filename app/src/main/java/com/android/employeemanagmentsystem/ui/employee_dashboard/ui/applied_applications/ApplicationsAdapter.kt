package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_applications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.databinding.ItemApplicationBinding
import com.android.employeemanagmentsystem.databinding.ItemTrainingsBinding
import com.android.employeemanagmentsystem.utils.getTrainingStatusById

class ApplicationsAdapter(
    private val applications: List<Application>,
    private val listener: ApplicationClickListener
): RecyclerView.Adapter<ApplicationsAdapter.TrainingViewHolder>() {

    interface ApplicationClickListener{
        fun onTrainingItemClicked(application: Application)
    }

    inner class TrainingViewHolder(val binding: ItemApplicationBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onTrainingItemClicked(applications[adapterPosition])
                }
            }
        }

        fun bindDate(application: Application){
            binding.apply {
                tvName.text = application.title
                tvDate.text = application.date
//                tvStatus.text = application.training_status_id.getTrainingStatusById()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding = ItemApplicationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrainingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bindDate(
            applications[position]
        )
    }

    override fun getItemCount() = applications.size

}