package com.example.yournexthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment? = supportFragmentManager.findFragmentById(R.id.nav_host_main) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let {
            NavigationUI.setupActionBarWithNavController(this, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
                true
            }
            R.id.mibtnLogout -> {
                //todo logout
                true
            }
            else -> navController?.let{ NavigationUI.onNavDestinationSelected(item, it)} ?: false
        }
    }
}