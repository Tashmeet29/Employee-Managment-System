package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.DsrRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentDepartmentsBinding
import com.android.employeemanagmentsystem.databinding.FragmentHostelsBinding
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_transfer_dept.*
import kotlinx.android.synthetic.main.fragment_transfer_dept.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Departments : Fragment(R.layout.fragment_departments), DepartmentsAdapter.ProductDistrClickListener {

    private lateinit var repository: DsrRepository
    private lateinit var api: DsrApi

    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private var p1 = 0
    private lateinit var binding: FragmentDepartmentsBinding
    private lateinit var dsr: List<String>
    lateinit var productDistr: List<ProductDistr>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

        binding = FragmentDepartmentsBinding.bind(view)
        repository = DsrRepository()
        api = DsrApi.invoke()
        showData()

        setSpinner()

        requestProduct.setOnClickListener {
            val action = DepartmentsDirections.actionDepartments2ToReqDept()
            findNavController().navigate(action)
        }

//        transfer.setOnClickListener{
//            val action = DepartmentsDirections.actionDepartments2ToTransferDept()
//            findNavController().navigate(action)
//        }

    }

    private fun setSpinner() {

        binding.apply {

            dsr = listOf(
                "Select Department",
                "Computer DSR",
                "IT DSR",
                "Mechanical DSR",
                "Civil DSR",
                "Electrical DSR",
                "Electronics DSR",
                "Pharmacy",
                "Chemical",
                "Plastic Polymer"
            )

            val ad: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                dsr
            )

            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            spinner2.adapter = ad

            spinner2.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        p1 = position
                        val item = parent.getItemAtPosition(position).toString()

                        showData()
                        Toast.makeText(parent.context, "Selected: $item", Toast.LENGTH_SHORT).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }
    }

    private fun showData() {
        GlobalScope.launch {

            val productDistr = repository.getDeptProduct(api, p1)


            withContext(Dispatchers.Main){

                binding.recyclerView.apply{
                    adapter = DepartmentsAdapter(productDistr, this@Departments)
                    layoutManager = LinearLayoutManager(requireContext())

//                    view?.transfer?.setOnClickListener{
//                        val action = DepartmentsDirections.actionDepartments2ToTransferDept()
//                        findNavController().navigate(action)
//                    }
                }


            }

        }
    }
    override fun onProductDistrItemClicked(productDistr: ProductDistr) {
        TODO("Not yet implemented")
    }
}

