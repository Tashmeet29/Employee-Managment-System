package com.android.employeemanagmentsystem.ui.training.applied_training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.databinding.ItemTrainingsBinding

class TrainingsAdapter(
    private val trainings: List<Training>,
    private val listener: TrainingClickListener
): RecyclerView.Adapter<TrainingsAdapter.TrainingViewHolder>() {

    interface TrainingClickListener{
        fun onTrainingItemClicked(training: Training)
    }

    inner class TrainingViewHolder(val binding: ItemTrainingsBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onTrainingItemClicked(trainings[adapterPosition])
                }
            }
        }

        fun bindDate(training: Training){
            binding.apply {
                tvName.text = training.name
                tvDuration.text = training.duration
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val binding = ItemTrainingsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrainingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bindDate(
            trainings[position]
        )
    }

    override fun getItemCount() = trainings.size

}