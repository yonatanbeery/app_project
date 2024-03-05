package com.example.yournexthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import base.ApiClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment? = supportFragmentManager.findFragmentById(R.id.nav_host_main) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let {
            NavigationUI.setupActionBarWithNavController(this, it)
        }
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loadCities()
            navController?.navigate(R.id.action_global_postsFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentUser = auth.currentUser

        return when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
                true
            }
            R.id.mibtnLogout -> {
                auth.signOut()
                navController?.navigateUp()
                true
            }
            else -> {
                if (currentUser != null) {
                    navController?.let{ NavigationUI.onNavDestinationSelected(item, it)} ?: false
                }
                true
            }
        }
    }

    private fun loadCities() {
        runBlocking {
            try {
                val response = ApiClient.cityService.getCities(
                    resourceId = "d4901968-dad3-4845-a9b0-a57d027f11ab",
                    limit = 32000
                )
                // Handle the response here
                println(response)
            } catch (e: Exception) {
                println("Error fetching data: $e")
            }
        }
    }
}