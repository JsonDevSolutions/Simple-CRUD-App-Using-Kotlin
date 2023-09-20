package com.example.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<ProductItem>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val price: TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = productList[position].name
        holder.price.text = "$" + productList[position].price.toString()

        holder.productName.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(productId = productList[position].id.toString())
            holder.itemView.findNavController().navigate(action)
        }
    }
}