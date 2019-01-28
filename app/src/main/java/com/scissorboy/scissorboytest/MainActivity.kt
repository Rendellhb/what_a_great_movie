package com.scissorboy.scissorboytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.scissorboy.scissorboytest.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.fragment_home -> {
//                item.actionView.findNavController().navigate(R.id.action_loginFragment_to_homeFragment_2)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.fragment_favorite -> {
//                item.actionView.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

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
}