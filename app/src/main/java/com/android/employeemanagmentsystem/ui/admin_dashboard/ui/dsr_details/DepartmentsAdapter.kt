package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.databinding.ItemEmployeeProductsBinding

private const val TAG = "DepartmentsProducts"

class DepartmentsAdapter(
    private val products: List<ProductDistr>,
    private val listener: ProductDistrClickListener
): RecyclerView.Adapter<DepartmentsAdapter.ProductDistrViewModel>() {

    interface ProductDistrClickListener{
        fun onProductDistrItemClicked(productDistr: ProductDistr)
    }


    inner class ProductDistrViewModel(val binding: ItemEmployeeProductsBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onProductDistrItemClicked(products[adapterPosition])
                }
            }
        }

        fun bindData(productDistr: ProductDistr){
            binding.apply {

                tvProductId.text = productDistr.Product_ID
                tvQtyDistributed.text = productDistr.qty_distributed
                tvDateDistributed.text = productDistr.date_distributed
                tvHeadInitials.text = productDistr.head_initials
                tvProductName.text = productDistr.product_name
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDistrViewModel {
        val binding = ItemEmployeeProductsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductDistrViewModel(binding)
    }

    override fun onBindViewHolder(holder: ProductDistrViewModel, position: Int) {
        holder.bindData(products[position])
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: " + products.size )
        return products.size
    }
}