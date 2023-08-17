package com.lefg095.misantlaapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.LoginActivityBinding
import com.lefg095.misantlaapp.util.ProviderTypes
import com.lefg095.misantlaapp.util.UPDATE_REQUEST_CODE

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private  lateinit var appUpdateManager: AppUpdateManager
    private lateinit var appUpdateInfoTask: Task<AppUpdateInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        appUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        appUpdateInfoTask = appUpdateManager.appUpdateInfo
        setContentView(view)
        validateUpdate()
        session()
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()
    }

    private fun validateUpdate() {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request an immediate update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    UPDATE_REQUEST_CODE
                )
            }
        }
    }

    private fun inProgressUpdate() {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() ==
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            ) {
                // Request an immediate update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    UPDATE_REQUEST_CODE
                )
            }
        }
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", "") ?: ""
        val provider = prefs.getString("provider", "DEFAULT")?: "DEFAULT"

        showHome(email, ProviderTypes.valueOf(provider))

//        if (email.isNotEmpty() && provider.isNotEmpty()) {
//            binding.authLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_4ever))
//            binding.authLayout.visibility = View.INVISIBLE
//            showHome(email, ProviderTypes.valueOf(provider))
//        }else{
//            showLogScream()
//        }
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