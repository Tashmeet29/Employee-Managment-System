package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.employeemanagmentsystem.data.models.responses.Product
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.databinding.ItemProductBinding
import kotlinx.android.synthetic.main.fragment_training_details.view.*

private const val TAG = "AppliedIoApplicationsAd"
class CentralStoreProductsAdapter(
    private val products: List<Product>,
    private val listener: ProductClickListener
    ): RecyclerView.Adapter<CentralStoreProductsAdapter.ProductViewModel>() {

    interface ProductClickListener{
        fun onProductItemClicked(product: Product)
        //fun onBtnDistrClick(product: Product)
    }


    inner class ProductViewModel(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onProductItemClicked(products[adapterPosition])
                }
            }
        }

        fun bindData(product: Product){
            binding.apply {

                tvOrganizationId.text = product.Organization_ID
                tvDsrNo.text = product.Organization_ID
                tvProductId.text = product.Product_ID
                tvPurchaseDate.text = product.DSR_no
                tvPurchaseAuthority.text = product.purchase_authority
                tvSupplierName.text = product.supplier_name
                tvSupplierAddress.text = product.Supplier_Address
                tvProductName.text = product.product_name
                tvProductDescr.text = product.product_desc
                tvQty.text = product.qty
                tvPricePerQty.text = product.Price_Per_Quantity
                tvQtyDistributed.text = product.Quantity_Distributed
                tvRemarks.text = product.remarks
                tvLastEdited.text = product.last_edited
                tvQtyRemaining.text = product.qty_remaining
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewModel {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewModel(binding)
    }

    override fun onBindViewHolder(holder: ProductViewModel, position: Int) {
        holder.bindData(products[position])
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: " + products.size )
        return products.size
    }


}