package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_applications

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Application
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.databinding.ItemApplicationBinding
import com.android.employeemanagmentsystem.databinding.ItemTrainingsBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_trainings.TrainingsAdapter

private const val TAG = "AppliedIoApplicationsAd"
class AppliedIoApplicationsAdapter(
    private val applications: List<Application>,
    private val listener: AppliedIoApplicationsAdapter.ApplicationClickListener
    ): RecyclerView.Adapter<AppliedIoApplicationsAdapter.AppliedApplicationsViewModel>() {

    interface ApplicationClickListener{
        fun onApplicationItemClicked(application: Application)
    }


    inner class AppliedApplicationsViewModel(val binding: ItemApplicationBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onApplicationItemClicked(applications[adapterPosition])
                }
            }
        }

        fun bindData(application: Application){
            binding.apply {

                tvId.text = application.id
                tvApplicantName.text = application.applicant_name
                tvName.text = application.title
                tvDate.text = application.date
                tvStatus.text = application.getApplicationStringStatus
                tvRemark.text = application.remark
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppliedApplicationsViewModel {
        val binding = ItemApplicationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppliedApplicationsViewModel(binding)
    }

    override fun onBindViewHolder(holder: AppliedApplicationsViewModel, position: Int) {
        holder.bindData(applications[position])
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: " + applications.size )
        return applications.size
    }


}