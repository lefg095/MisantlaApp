package com.lefg095.misantlaapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.LoginActivityBinding
import com.lefg095.misantlaapp.util.ProviderTypes

class SplashActivity: AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        session()
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            binding.authLayout.setBackgroundColor(resources.getColor(R.color.blue_4ever))
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderTypes.valueOf(provider))
        }else{
            showLogScream()
        }
    }

    private fun showHome(email: String, provider: ProviderTypes) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
        finish()
    }

    private fun showLogScream(){
        val i = Intent(this, AuthActivity::class.java).apply {
        }
        startActivity(i)
        finish()
    }
}