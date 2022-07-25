package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.databinding.ActivityDsrBinding

private const val TAG2 = "DSRFra"

class Dsr : Fragment(R.layout.activity_dsr), DsrAdapter.ProductDistrClickListener{

    private lateinit var binding: ActivityDsrBinding
    private lateinit var dsr: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityDsrBinding.bind(view)

        setSpinner()

    }

    private fun setSpinner() {

        binding.apply {

            dsr = listOf(
                "Select DSR",
                "Central Store DSR",
                "Department DSR",
                "Hostels DSR",
                "Library DSR",
                "Office DSR",
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
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val item = parent?.getItemAtPosition(position).toString()
                        Toast.makeText(parent?.context, "Selected: $item", Toast.LENGTH_SHORT).show()
                        if (parent?.getItemAtPosition(position) == "Select DSR") {

                            // do nothing

                        }else{
                            if (parent?.getItemAtPosition(position) == "Central Store DSR") {
                                val action = DsrDirections.actionNavDsrToNavCentralStoreDsr(1)
                                findNavController().navigate(action)
                            }
                            if (parent?.getItemAtPosition(position) == "Department DSR"){
                                val action = DsrDirections.actionNavDsrToDepartments2(2)
                                findNavController().navigate(action)
                            }
                            if (parent?.getItemAtPosition(position) == "Hostels DSR"){
                                val action = DsrDirections.actionNavDsrToHostels(3)
                                findNavController().navigate(action)
                            }
                            if (parent?.getItemAtPosition(position) == "Library DSR"){
                                val action = DsrDirections.actionNavDsrToLibrary(4)
                                findNavController().navigate(action)
                            }
                            if (parent?.getItemAtPosition(position) == "Office DSR"){
                                val action = DsrDirections.actionNavDsrToOffice(5)
                                findNavController().navigate(action)
                            }
                    }

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }
    }



    override fun onProductDistrItemClicked(productDistr: ProductDistr) {
        TODO("Not yet implemented")
    }
}





