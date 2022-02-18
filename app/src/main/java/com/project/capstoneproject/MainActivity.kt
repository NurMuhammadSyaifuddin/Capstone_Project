package com.project.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.project.capstoneproject.databinding.ActivityMainBinding
import com.project.capstoneproject.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()

        setUpNavController()
    }

    private fun setUpNavController() {
        val navController = findNavController(R.id.nav_host_controller)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_movie,
            R.id.navigation_tv_show,
            R.id.navigation_favorite,
            R.id.navigation_user_profile
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavMenu.setupWithNavController(navController)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                Intent(this, SettingActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}