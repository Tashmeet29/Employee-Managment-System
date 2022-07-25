package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.databinding.ItemEmployeeProductsBinding


private const val TAG = "TrainingApplicationsFra"

class DsrAdapter(
    private val productDistr: List<ProductDistr>,
    private val listener: ProductDistrClickListener
): RecyclerView.Adapter<DsrAdapter.ProductDistrViewHolder>() {

    interface ProductDistrClickListener{
        fun onProductDistrItemClicked(productDistr: ProductDistr)
    }

    inner class ProductDistrViewHolder(val binding: ItemEmployeeProductsBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onProductDistrItemClicked(productDistr[adapterPosition])
                }
            }
        }

        fun bindData(productDistr: ProductDistr){
            binding.apply {
                tvProductId.text = productDistr.Product_ID
                tvQtyDistributed.text = productDistr.qty_distributed
                tvDateDistributed.text = productDistr.date_distributed
                tvHeadInitials.text = productDistr.head_initials
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDistrViewHolder {
        val binding = ItemEmployeeProductsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductDistrViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ProductDistrViewHolder, position: Int) {
        holder.bindData(
            productDistr[position]
        )
    }

    override fun getItemCount() = productDistr.size

}