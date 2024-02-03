package com.example.yournexthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

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
}