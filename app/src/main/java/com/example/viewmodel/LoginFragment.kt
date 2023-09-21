package com.example.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.viewmodel.databinding.FragmentLoginBinding
import com.example.viewmodel.ui.theme.AuthViewModel


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

//    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
//        name = "user"
//    )
//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnLogin = binding.btnLogin
        btnLogin?.setOnClickListener{
            onLogin()
            viewModel.loginResponse.observe(viewLifecycleOwner
            ) { response ->
                if(response !== null){
                    val action = LoginFragmentDirections.actionLoginFragmentToProductListFragment()
                    btnLogin?.findNavController()?.navigate(action)
                }
            }
        }

        val navRegister = binding.navRegister
        navRegister?.setOnClickListener{

            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            navRegister?.findNavController()?.navigate(action)
        }


        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun onLogin(){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        viewModel.login(email, password)
    }
}