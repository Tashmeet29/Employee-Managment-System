package com.android.employeemanagmentsystem.ui.registration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Organizations

class OrganizationsAdapter (
    val context: Context,
    val organizations: List<Organizations>
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
        vh.type.text = organizations[position].org_name

        return view
    }

    override fun getItem(position: Int): Any? {
        return organizations[position];
    }

    override fun getCount(): Int {
        return organizations.size;
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

