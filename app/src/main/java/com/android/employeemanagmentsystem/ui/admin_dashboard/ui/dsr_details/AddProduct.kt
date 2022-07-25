package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.repository.DsrRepository
import com.android.employeemanagmentsystem.databinding.FragmentAddProductBinding
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*

class AddProduct : Fragment(R.layout.fragment_add_product) {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var auth: List<String>
    private lateinit var dsrRepo: DsrRepository
    private lateinit var dsrApi: DsrApi

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddProductBinding.bind(view)

        handleClickOfAddProductButton()

        dsrRepo = DsrRepository()
        dsrApi = DsrApi()

        setSpinner()

    }

    private fun setSpinner() {

        binding.apply {

            auth = listOf(
                "Choose Authority",
                "Principal",
                "DTE",
                "JD",
            )

            val ad: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                auth
            )

            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            spinnerAuth.adapter = ad

            spinnerAuth.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val item = parent?.getItemAtPosition(position).toString()
                        Toast.makeText(parent?.context, "Selected: $item", Toast.LENGTH_SHORT)
                            .show()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleClickOfAddProductButton() {

        binding.apply {


            submit.setOnClickListener {

                val Organization_ID = tvOrganizationId.text.toString()
                val DSR_no = tvDsrNo.text.toString()
                val purchase_date = tvPurchaseDate.text.toString()
                val supplier_name = tvSupplierName.text.toString()
                val Supplier_Address = tvSupplierAddress.text.toString()
                val product_name = tvProductName.text.toString()
                val product_desc = tvProductDescr.text.toString()
                val qty = tvQty.text.toString()
                val Price_Per_Quantity = tvPricePerQty.text.toString()
                val Quantity_Distributed = tvQtyDistributed.text.toString()
                val remarks = tvRemarks.text.toString()

                when {

                    Organization_ID.isBlank() -> requireContext().toast("Please Enter Organization ID")
                    DSR_no.isBlank() -> requireContext().toast("Please Enter DSR no.")
                    purchase_date.isBlank() -> requireContext().toast("Please Enter Purchase Date")
                    supplier_name.isBlank() -> requireContext().toast("Please Enter Supplier Name")
                    Supplier_Address.isBlank() -> requireContext().toast("Please Enter Supplier Address")
                    product_name.isBlank() -> requireContext().toast("Please Enter Product Name")
                    product_desc.isBlank() -> requireContext().toast("Please Enter Product Description")
                    qty.isBlank() -> requireContext().toast("Please Enter Quantity")
                    Price_Per_Quantity.isBlank() -> requireContext().toast("Please Enter Price per Quantity")
                    Quantity_Distributed.isBlank() -> requireContext().toast("Please Enter Quantity Distributed")
                    remarks.isBlank() -> requireContext().toast("Please Enter Remarks")

                      else -> {

                        GlobalScope.launch {

                            try {

                                withContext(Dispatchers.Main) {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      }

                                val dsrResponse = dsrRepo.addCsProduct(
                                    Organization_ID = Organization_ID,
                                    DSR_no = DSR_no,
                                    purchase_date = purchase_date,
                                    supplier_name = supplier_name,
                                    Supplier_Address = Supplier_Address,
                                    product_name = product_name,
                                    product_desc = product_desc,
                                    qty = qty,
                                    Price_Per_Quantity = Price_Per_Quantity,
                                    Quantity_Distributed = Quantity_Distributed,
                                    remarks = remarks,
                                    api = dsrApi
                                    )

                                delay((1 * 1000).toLong())

                            } catch (e: Exception) {

                                requireContext().handleException(e)

                            }

                        }


                    }
                }

            }
        }
    }
}


