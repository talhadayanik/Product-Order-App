package com.example.ProductOrderApp.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ProductOrderApp.CartOrderProduct
import com.example.ProductOrderApp.R

class OrdersItemAdapter(private val context : Activity, private val list : MutableList<CartOrderProduct>) : ArrayAdapter<CartOrderProduct>(context, R.layout.order_list_item, list){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = context.layoutInflater.inflate(R.layout.order_list_item, null, true)

        val title = view.findViewById<TextView>(R.id.txtOrderTitle)
        val price = view.findViewById<TextView>(R.id.txtOrderPrice)
        val id = view.findViewById<TextView>(R.id.txtOrderId)
        val product = list.get(position)

        title.text = product.title
        price.text = product.price.toString() + " TL"
        id.text = "id: ${product.id}"

        return view
    }

    public fun updateList(newList: MutableList<CartOrderProduct>){
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }
}