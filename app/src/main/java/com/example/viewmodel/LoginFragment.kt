package com.example.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class LoginFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val login = view?.findViewById<TextView>(R.id.btn_login)
        login?.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToProductListFragment()
            login?.findNavController()?.navigate(action)
        }
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}