package com.example.ecommerce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ecommerce.R
import com.example.ecommerce.USER_ID
import com.example.ecommerce.databinding.ActivityMainBinding
import com.example.ecommerce.datastore.LoggedInUserDetails
import com.example.ecommerce.model.dataclasses.LoginData
import com.example.ecommerce.view.fragments.LoginFragmentDirections
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController

        // Retrieve login user details
        val dataStore = LoggedInUserDetails(this)
        val gson = Gson()
        val scope = lifecycleScope
        scope.launch {
            val loginData = gson.fromJson(dataStore.getLoggedInUserDetails.firstOrNull(), LoginData::class.java)

            if(loginData !== null) {
                USER_ID = loginData.id

                // Navigate to homepage if user is already login
                val action = LoginFragmentDirections.actionLoginFragmentToProductListFragment()
                navController.navigate(action)
            }
        }

        // Make sure actions in the ActionBar get propagated to the NavController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}