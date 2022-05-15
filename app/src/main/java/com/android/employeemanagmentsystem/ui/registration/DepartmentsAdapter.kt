package com.android.employeemanagmentsystem.ui.registration


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Department

class DepartmentsAdapter (
    val context: Context,
    val departments: List<Department>
): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_training_types, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.type.text = departments[position].dept_name

        return view
    }

    override fun getItem(position: Int): Any? {
        Log.d("Dep: ","${departments[position]}")

        return departments[position];
    }

    override fun getCount(): Int {
        return departments.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val type: TextView

        init {
            type = row?.findViewById(R.id.tv_training_type) as TextView
        }
    }

}

