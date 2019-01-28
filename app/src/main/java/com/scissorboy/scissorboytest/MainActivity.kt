package com.scissorboy.scissorboytest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.scissorboy.scissorboytest.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setSupportActionBar(binding.toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding.navigation.setupWithNavController(navController)

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout -> {
                val options: NavOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()
                navController.navigate(R.id.loginFragment,null , options)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}