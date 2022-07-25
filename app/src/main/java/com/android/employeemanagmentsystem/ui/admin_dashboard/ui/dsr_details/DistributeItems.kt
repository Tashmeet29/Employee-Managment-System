package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Product
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.repository.DsrRepository
import com.android.employeemanagmentsystem.databinding.FragmentDistributeItemsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DistributeItems : Fragment(R.layout.fragment_distribute_items) {

    private lateinit var binding: FragmentDistributeItemsBinding
    private lateinit var dsr: List<String>

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDistributeItemsBinding.bind(view)

        setSpinner()

    }

    //override fun onBtnDistrClick(product: Product) {

        //when {
          //  else -> {
            //    GlobalScope.launch {
              //      try {
                //        //making network call to get user credentials
                  //      val product: StatusResponse =
                    //        DsrRepository.getCsProduct(products.Product_ID, DsrApi)

                        //saving the employee in local database
                      //  if (product.status == "true") {
                        //    withContext(Dispatchers.Main) {
                          //      getCsProduct()
                            //    Toast.makeText(context, "Product Distributed!", Toast.LENGTH_SHORT).show()
                            //}


                        //} else {
                          //  Dispatchers.Main {
                            //    Toast.makeText(context, "Distribution Failed", Toast.LENGTH_SHORT).show()
                              //  getCsproduct()
                            //}
                        //}


                    //} catch (e: Exception) {
                      //  Toast.makeText(context, "Error $e", Toast.LENGTH_SHORT).show()
                    //}

                //}
            //}
        //}
    //}
    private fun setSpinner() {

        binding.apply {

            dsr = listOf(
                "Computer",
                "Civil",
                "Electrical",
                "Electronics",
                "Mechanical",
                "IT",
                "Pharmacy",
                "Chemical",
                "Plastic Polymer",
                "Girls Hostel",
                "Boys Hostel",
                "Library",
                "Office"
            )

            val ad: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                dsr
            )

            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            spinner.adapter = ad

            spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        val item = parent.getItemAtPosition(position).toString()
                        Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_SHORT).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }
    }
}
