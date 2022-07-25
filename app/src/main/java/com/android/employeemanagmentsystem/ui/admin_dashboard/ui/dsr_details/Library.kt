package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Product
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.repository.DsrRepository
import com.android.employeemanagmentsystem.databinding.ActivityCsDsrBinding
import com.android.employeemanagmentsystem.databinding.FragmentLibraryBinding
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr.CentralStoreProductsAdapter
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr.CsDSRDirections
import kotlinx.android.synthetic.main.activity_cs_dsr.*
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.fragment_transfer_dept.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Library : Fragment(R.layout.fragment_library), LibraryAdapter.ProductDistrClickListener {

    private lateinit var repository: DsrRepository
    private lateinit var api: DsrApi
    private lateinit var binding: FragmentLibraryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLibraryBinding.bind(view)
        repository = DsrRepository()
        api = DsrApi.invoke()
        showData()

//        transfer.setOnClickListener{
//            val action = LibraryDirections.actionLibraryToTransferLibrary()
//            findNavController().navigate(action)
//        }
        requestProduct.setOnClickListener {
            val action = LibraryDirections.actionLibraryToReqLibrary()
            findNavController().navigate(action)
        }

    }

    private fun showData() {

        GlobalScope.launch {
            val products =  repository.getLibProduct(api)

            withContext(Dispatchers.Main){

                binding.recyclerView.apply {
                    adapter = LibraryAdapter(products, this@Library)
                    layoutManager = LinearLayoutManager(requireContext())

//                    transfer.setOnClickListener{
//                        val action = LibraryDirections.actionLibraryToTransferLibrary()
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


