package com.example.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.name)
        val price: TextView = view.findViewById(R.id.price)
        val quantity: TextView = view.findViewById(R.id.quantity)
        val total: TextView = view.findViewById(R.id.total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartItems.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cartItems[position]

        holder.productName.text = item.product.name
        holder.price.text = "$" + item.product.price
        holder.quantity.text = item.quantity.toString()
        holder.total.text = "$" + (item.product.price.toInt() * item.quantity)

        holder.productName.setOnClickListener{
            val action = CartPageFragmentDirections.actionCartPageFragmentToProductDetailsFragment(productId = item.product.id.toString())
            holder.itemView.findNavController().navigate(action)
        }
    }
}