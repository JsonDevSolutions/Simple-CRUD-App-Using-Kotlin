package com.example.ecommerce.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.ecommerce.model.dataclasses.CartRequest
import com.example.ecommerce.USER_ID
import com.example.ecommerce.databinding.FragmentProductDetailsBinding
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.ProductViewModel

class ProductDetailsFragment : Fragment() {
    private val viewModel: ProductViewModel by viewModels()
    private val viewModelCart: CartViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
        arguments?.let {
            productId = it.getString("productId").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        viewModel.productDetails(productId).observe(viewLifecycleOwner
        ) { details ->
            val txtProductName = binding.productName
            val txtDescription = binding.description
            val txtPrice = binding.price
            val image: ImageView = binding.productImageDetails
            val addToCart: Button = binding.btnAddToCart

            txtProductName.text = details.name
            txtDescription.text = details.description
            txtPrice.text = "$" + details.price.toString()

            val imageLoader = view?.context?.let {
                ImageLoader.Builder(it)
                    .crossfade(true) // Optional: Enable crossfade animation
                    .build()
            }

            val request = view?.let {
                ImageRequest.Builder(it.context)
                    .data(details.image)
                    .target(image)
                    .build()
            }

            // Load the image using Coil
            if (request != null) {
                imageLoader?.enqueue(request)
            }

            addToCart.setOnClickListener{
                val cart = CartRequest(USER_ID, details.id, 1)
                viewModelCart.addCart(cart)
            }
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
        _binding = null
    }
}