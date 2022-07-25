package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Product
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.repository.DsrRepository
import com.android.employeemanagmentsystem.databinding.ActivityCsDsrBinding
import kotlinx.android.synthetic.main.activity_cs_dsr.*
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "CsDSRFra"

class CsDSR : Fragment(R.layout.activity_cs_dsr), CentralStoreProductsAdapter.ProductClickListener {

    private lateinit var repository: DsrRepository
    private lateinit var api: DsrApi
    private lateinit var binding: ActivityCsDsrBinding
    private lateinit var products: List<Product>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityCsDsrBinding.bind(view)
        repository = DsrRepository()
        api = DsrApi.invoke()
        showData()

        getCsProduct()

        add.setOnClickListener {
            val action = CsDSRDirections.actionNavCentralStoreDsrToAddProduct()
            findNavController().navigate(action)
        }

//        btn_distr.setOnClickListener {
//            val action = CsDSRDirections.actionNavCentralStoreDsrToDistributeItems()
//            findNavController().navigate(action)
//        }

//        fragmentManager?.popBackStack(
//            TAG,
//            FragmentManager.POP_BACK_STACK_INCLUSIVE
//        )
//        val newNavHostFragment =
//            fragmentManager?.findFragmentByTag(TAG) as NavHostFragment?
//        fragmentManager?.beginTransaction()
//            ?.attach(newNavHostFragment!!)
//            ?.setPrimaryNavigationFragment(newNavHostFragment)
//            ?.addToBackStack(TAG)
//            ?.setReorderingAllowed(true)
//            ?.commit()

    }

    //override fun onBackPressed() {
      //  val action = DsrDirections.actionNavCentralStoreDsrToNavDsr()
        //findNavController().navigate(action)
    //}


    private fun getCsProduct() {
        val dsrRepository = DsrRepository()
        val dsrApi = DsrApi.invoke()

        GlobalScope.launch {

            products = dsrRepository.getCsProduct(dsrApi)
            Log.d("DSRPRo","$products")
            withContext(Dispatchers.Main) {
                binding.recyclerView.apply {
                    adapter = CentralStoreProductsAdapter(products, this@CsDSR)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

    private fun showData() {

        GlobalScope.launch {
           val products =  repository.getCsProduct(api)

            withContext(Dispatchers.Main){

                binding.recyclerView.apply {
                    adapter = CentralStoreProductsAdapter(products, this@CsDSR)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }

        }
    }

    override fun onProductItemClicked(product: Product) {
        TODO("Not yet implemented")
    }
}





