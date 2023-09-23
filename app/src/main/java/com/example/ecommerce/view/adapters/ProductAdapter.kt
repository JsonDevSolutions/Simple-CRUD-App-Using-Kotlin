package com.example.ecommerce.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.ecommerce.model.dataclasses.CartRequest
import com.example.ecommerce.model.dataclasses.Product
import com.example.ecommerce.R
import com.example.ecommerce.USER_ID
import com.example.ecommerce.view.fragments.ProductListFragmentDirections
import com.example.ecommerce.viewmodel.CartViewModel

class ProductAdapter(private val productList: List<Product>, private val viewModel: CartViewModel) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val price: TextView = view.findViewById(R.id.amount)
        val addToCart: Button = view.findViewById(R.id.btn_add_to_cart)
        val quantity: EditText = view.findViewById(R.id.quantity_cart)
        private val image: ImageView = view.findViewById(R.id.product_image)

        fun bindImage(imageUrl: String) {
            val imageLoader = ImageLoader.Builder(itemView.context)
                .crossfade(true) // Optional: Enable crossfade animation
                .build()

            val request = ImageRequest.Builder(itemView.context)
                .data(imageUrl)
                .target(image)
                .build()

            // Load the image using Coil
            imageLoader.enqueue(request)
        }
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

        holder.bindImage(productList[position].image)

        holder.productName.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(productId = productList[position].id.toString())
            holder.itemView.findNavController().navigate(action)
        }

        holder.addToCart.setOnClickListener{
            val cart = CartRequest(USER_ID, productList[position].id, holder.quantity.text.toString().toInt())
            viewModel.addCart(cart)
        }
    }
}