package com.project.capstoneproject.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.project.capstoneproject.databinding.ActivitySettingBinding
import com.project.capstoneproject.ui.login.LoginActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()

        setUpToolbar()
    }

    private fun onAction() {
        binding.apply {
            tvChangeLanguage.setOnClickListener {
                Intent(Settings.ACTION_LOCALE_SETTINGS).also { intent ->
                    startActivity(intent)
                }
            }

            val intentToLogin = {
                Intent(this@SettingActivity, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                    finishAffinity()
                }
            }

            tvLogout.setOnClickListener {
                auth.signOut()
                GoogleSignIn.getClient(applicationContext, GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .signOut()
                intentToLogin()


            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}