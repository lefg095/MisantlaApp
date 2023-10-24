package com.lefg095.misantlaapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.lefg095.misantlaapp.util.ProviderTypes
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.LoginActivityBinding
import com.lefg095.misantlaapp.util.alertAuth
import com.lefg095.misantlaapp.util.alertWarning


class AuthActivity: AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding: LoginActivityBinding
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MisantlaAppNoBar)
        firebaseAnalytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*//Analitycs Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle)*/

       /* //Setup
        setup()

        session()*/
    }

  /*  override fun onRestart() {
        super.onRestart()
        binding.authLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.authLayout.visibility = View.VISIBLE

    }*/

/*    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", "") ?: ""
        val provider = prefs.getString("provider", "DEFAULT") ?: "DEFAULT"

        if (email.isNotEmpty() && provider.isNotEmpty()) {
            binding.authLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_4ever))
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderTypes.valueOf(provider))
        }
    }*/

   /* private fun setup() {
        title = "Authentication"
        val pInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)
        val version: String = pInfo.versionName
        binding.tvLogVersion.text = getString(R.string.vs_str, version)
        binding.btnGogleAuth.setOnClickListener {
          *//*  //Configuracion
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleclient = GoogleSignIn.getClient(this, googleConf)
            googleclient.signOut()
            startActivityForResult(googleclient.signInIntent, GOOGLE_SIGN_IN)
*//*
        }
        binding.btnLogin.setOnClickListener {
            if (binding.itEmail.text!!.isNotEmpty() && binding.itPassword.text!!.isNotEmpty()){
                val usr = binding.itEmail.text.toString().trim()
                val pass = binding.itPassword.text.toString().trim()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    usr,
                    pass
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(binding.itEmail.text.toString(), ProviderTypes.BASIC)
                    } else {
                        alertAuth(this)
                    }
                }
            } else {
                alertWarning(this, "Alerta", "Ingrese un usuario y contraseña")
            }
        }
        binding.btnRegistrarse.setOnClickListener {
            if (binding.itEmail.text!!.isNotEmpty() && binding.itPassword.text!!.isNotEmpty()){
                val usr = binding.itEmail.text.toString().trim()
                val pass = binding.itPassword.text.toString().trim()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    usr, pass
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(binding.itEmail.text.toString(), ProviderTypes.BASIC)
                    } else {
                        alertAuth(this)
                    }
                }

            } else {
                alertWarning(this, "Alerta", "Ingrese un usuario y contraseña")
            }
        }
    }*/
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)*/

       /* if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credentials).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(account.email ?: "", ProviderTypes.GOOGLE)
                        } else {
                            alertAuth(this)
                        }

                    }
                }
            } catch (e: ApiException) {
                tracker("Error", "Login", "$e")
                Log.e("Login", "$e")
                alertAuth(this)
            }*/

   /*     }
    }
*/
   /* private fun showHome(email: String, provider: ProviderTypes) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
        finish()
    }*/

 /*   private fun tracker(id: String, name: String, content: String){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            this.param(FirebaseAnalytics.Param.ITEM_ID, id)
            this.param(FirebaseAnalytics.Param.ITEM_NAME, name)
            this.param(FirebaseAnalytics.Param.CONTENT_TYPE, content)
        }
    }*/
}