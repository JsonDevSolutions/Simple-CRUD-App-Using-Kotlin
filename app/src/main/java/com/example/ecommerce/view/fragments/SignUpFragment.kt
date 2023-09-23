package com.example.ecommerce.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.ecommerce.model.dataclasses.User
import com.example.ecommerce.databinding.FragmentSignUpBinding
import com.example.ecommerce.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {
    private var isPassword = true
    private val viewModel: AuthViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val login = binding.viewLogin
        login.setOnClickListener{
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            login.findNavController().navigate(action)
        }

        val btnSignup = binding.btnSignup
        btnSignup.setOnClickListener{
            onSignUp()
            viewModel.signUpResponse.observe(viewLifecycleOwner
            ) { response ->
                if(response !== null){
                    clearTextValues()
                    binding.registrationMessage.visibility = View.VISIBLE
                }
            }
        }

        val showPassword = binding.showPassword
        showPassword.setOnClickListener{
            val password = binding.password

            isPassword = !isPassword

            if(isPassword) {
                password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                password.inputType = InputType.TYPE_CLASS_TEXT
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    private fun clearTextValues() {
        val emptyString = Editable.Factory.getInstance().newEditable("")
        binding.firstName.text = emptyString
        binding.lastName.text = emptyString
        binding.email.text = emptyString
        binding.password.text = emptyString
    }
}