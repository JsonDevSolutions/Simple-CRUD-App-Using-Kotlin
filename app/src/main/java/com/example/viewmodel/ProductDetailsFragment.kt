package com.example.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.viewmodel.databinding.FragmentProductDetailsBinding
import com.example.viewmodel.ui.theme.ProductViewModel

class ProductDetailsFragment : Fragment() {
    private val viewModel: ProductViewModel by viewModels()
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
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        viewModel.productDetails(productId).observe(viewLifecycleOwner
        ) { details ->
            val txtProductName = view?.findViewById<TextView>(R.id.product_name)
            val txtDescription = view?.findViewById<TextView>(R.id.description)
            val txtPrice = view?.findViewById<TextView>(R.id.price)

            txtProductName?.text = details.name
            txtDescription?.text = details.description
            txtPrice?.text = details.price.toString()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
        _binding = null
    }
}