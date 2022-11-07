package com.lefg095.misantlaapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.AboutActivityBinding

class AboutActivity: AppCompatActivity() {
    private lateinit var binding: AboutActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MisantlaAppNoBar)
        super.onCreate(savedInstanceState)
        binding = AboutActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val pInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)
        val version: String = pInfo.versionName
        binding.tvVersion.text = "Version: ${version}"
        binding.tvDevs.text = "Diseñado y desarrollado por:\n LINEL IT Systems and Solutions \n\n en colaboración con: \n Artes Graficas Misantla"
    }
}