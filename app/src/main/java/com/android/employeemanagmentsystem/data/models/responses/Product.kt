package com.android.employeemanagmentsystem.data.models.responses


data class Product(
    val Organization_ID: String,
    val DSR_no: String,
    val Product_ID: String,
    val purchase_date: String,
    val purchase_authority: String,
    val supplier_name: String,
    val Supplier_Address: String,
    val product_name: String,
    val product_desc: String,
    val qty: String,
    val Price_Per_Quantity: String,
    val Quantity_Distributed: String,
    val remarks: String,
    val last_edited: String,
    val price: String,
    val qty_remaining: String,
)