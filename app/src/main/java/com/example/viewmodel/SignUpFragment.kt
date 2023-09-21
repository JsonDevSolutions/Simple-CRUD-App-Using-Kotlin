package com.example.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.viewmodel.databinding.FragmentProductDetailsBinding
import com.example.viewmodel.databinding.FragmentSignUpBinding
import com.example.viewmodel.ui.theme.AuthViewModel

class SignUpFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val login = binding.viewLogin
        login?.setOnClickListener{
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            login?.findNavController()?.navigate(action)
        }

        val btnSignup = binding.btnSignup
        btnSignup.setOnClickListener{
            onSignUp()
            viewModel.signUpResponse.observe(viewLifecycleOwner
            ) { response ->
                if(response !== null){
                    Log.d("AuthActivity", "On success: $response")
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onSignUp(){
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        val user = User(firstName, lastName, email, password)
        viewModel.signUp(user)
    }
}