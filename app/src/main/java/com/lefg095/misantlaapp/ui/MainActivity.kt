package com.lefg095.misantlaapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var appBarConfiguration: AppBarConfiguration
    var provider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        toggle = ActionBarDrawerToggle(this, binding.mlayout, R.string.open, R.string.close)
        binding.mlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                //R.id.it_config -> Toast.makeText(applicationContext, "Item one", Toast.LENGTH_LONG).show()
//                R.id.it_close_session->{
//                    //Borrado de datos
//                    val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
//                    prefs.clear()
//                    prefs.apply()
//
//                    FirebaseAuth.getInstance().signOut()
//                    val i = Intent(this, AuthActivity::class.java).apply {
//                    }
//                    startActivity(i)
//                    finish()
//                }
                R.id.it_about_this->{
                    tracker("it_about_this", "btn_acerca_de","click")
                    val i = Intent(this, AboutActivity::class.java).apply {
                    }
                    startActivity(i)
                }
                R.id.it_contac->{
                    val i = Intent(this, ContactActivity::class.java).apply {
                    }
                    startActivity(i)
                }
            }
            binding.mlayout.closeDrawer(GravityCompat.START)
            true
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

        appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph, binding.mlayout)
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)

        //Setup
        val bundle = intent.extras
        val email = bundle!!.getString("email")
        provider = bundle.getString("provider")!!

        //Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)||super.onSupportNavigateUp()
        //onBackPressed()
    }

    private fun tracker(id: String, name: String, content: String){
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            this.param(FirebaseAnalytics.Param.ITEM_ID, id)
            this.param(FirebaseAnalytics.Param.ITEM_NAME, name)
            this.param(FirebaseAnalytics.Param.CONTENT_TYPE, content)
        }
    }
}