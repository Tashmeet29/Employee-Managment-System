package com.android.employeemanagmentsystem.data.repository

import com.android.employeemanagmentsystem.data.models.responses.Product
import com.android.employeemanagmentsystem.data.models.responses.ProductDistr
import com.android.employeemanagmentsystem.data.models.responses.StatusResponse
import com.android.employeemanagmentsystem.data.network.SafeApiRequest
import com.android.employeemanagmentsystem.data.network.apis.DsrApi
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.ui.admin_dashboard.ui.dsr_details.Library
import com.android.employeemanagmentsystem.utils.toMultipartReq
import okhttp3.MultipartBody

class DsrRepository : SafeApiRequest() {
    suspend fun getCsProduct(
        api: DsrApi
    ): List<Product> {

        return apiRequest {
            api.getCsProduct()
        }

    }
    suspend fun addCsProduct(
        Organization_ID: String,
        DSR_no: String,
        purchase_date: String,
        supplier_name: String,
        Supplier_Address: String,
        product_name: String,
        product_desc: String,
        qty: String,
        Price_Per_Quantity: String,
        Quantity_Distributed: String,
        remarks: String,
        api: DsrApi
    ) = apiRequest {
        api.addCsProduct(
            Organization_ID.toMultipartReq(),
            DSR_no.toMultipartReq(),
            purchase_date.toMultipartReq(),
            supplier_name.toMultipartReq(),
            Supplier_Address.toMultipartReq(),
            product_name.toMultipartReq(),
            product_desc.toMultipartReq(),
            qty.toMultipartReq(),
            Price_Per_Quantity.toMultipartReq(),
            Quantity_Distributed.toMultipartReq(),
            remarks.toMultipartReq(),
        )
    }

    suspend fun getDeptProduct(
        api: DsrApi,
        position: Int
    ): List<ProductDistr> {
        return apiRequest {
            api.getDeptProduct(position)
        }

    }
    suspend fun getHostProduct(
        api: DsrApi,
        position: Int
    ): List<ProductDistr> {

        return apiRequest {
            api.getHostProduct(position)
        }

    }
    suspend fun getLibProduct(
        api: DsrApi,
    ): List<ProductDistr> {

        return apiRequest {
            api.getLibProduct()
        }

    }
    suspend fun getOfficeProduct(
        api: DsrApi
    ): List<ProductDistr> {

        return apiRequest {
            api.getOfficeProduct()
        }

    }

}