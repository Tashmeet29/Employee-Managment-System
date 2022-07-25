package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.repository.DsrRepository
import com.android.employeemanagmentsystem.databinding.FragmentLibraryBinding
import com.android.employeemanagmentsystem.databinding.FragmentOfficeBinding
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_transfer_dept.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Office : Fragment(R.layout.fragment_office), OfficeAdapter.ProductDistrClickListener {

    private lateinit var repository: DsrRepository
    private lateinit var api: DsrApi
    private lateinit var binding: FragmentOfficeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOfficeBinding.bind(view)
        repository = DsrRepository()
        api = DsrApi.invoke()
        showData()

//        transfer.setOnClickListener{
//            val action = OfficeDirections.actionOfficeToTransferOffice()
//            findNavController().navigate(action)
//        }
        requestProduct.setOnClickListener {
            val action = OfficeDirections.actionOfficeToReqOffice()
            findNavController().navigate(action)
        }
    }

    private fun showData() {

        GlobalScope.launch {
            val products =  repository.getOfficeProduct(api)

            withContext(Dispatchers.Main){

                binding.recyclerView.apply {
                    adapter = OfficeAdapter(products, this@Office)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }

        }
    }

    override fun onProductDistrItemClicked(productDistr: ProductDistr) {
        TODO("Not yet implemented")
    }
}