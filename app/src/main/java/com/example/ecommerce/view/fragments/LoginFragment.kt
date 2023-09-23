package com.example.ecommerce.view.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.ecommerce.model.dataclasses.LoginData
import com.example.ecommerce.USER_ID
import com.example.ecommerce.databinding.FragmentLoginBinding
import com.example.ecommerce.datastore.LoggedInUserDetails
import com.example.ecommerce.viewmodel.AuthViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var isPassword = true
        val btnLogin = binding.btnLogin
        val scope = viewLifecycleOwner.lifecycleScope
        val dataStore = context?.let { it1 -> LoggedInUserDetails(it1) }
        btnLogin.setOnClickListener{
            onLogin()
            viewModel.loginResponse.observe(viewLifecycleOwner
            ) { response ->
                if(response !== null){
                    scope.launch {
                        // Save login response to storage
                        dataStore?.saveLoginDetails(response.data)

                        // Update logged in details after login
                        val gson = Gson()
                        val loginData = gson.fromJson(dataStore?.getLoggedInUserDetails?.firstOrNull(), LoginData::class.java)
                        viewModel.updateUserData(loginData)
                        USER_ID = response.data.id
                        // Navigate to homepage
                        val action = LoginFragmentDirections.actionLoginFragmentToProductListFragment()
                        btnLogin.findNavController()?.navigate(action)
                    }
                } else {
                    binding.loginMessage.visibility = View.VISIBLE
                }
            }
        }

        val navRegister = binding.navRegister
        navRegister.setOnClickListener{

            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            navRegister.findNavController()?.navigate(action)
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

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onLogin(){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        viewModel.login(email, password)
    }
}