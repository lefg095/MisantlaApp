package com.lefg095.misantlaapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.ContactActivityBinding
import com.lefg095.misantlaapp.ui.business.adapter.ContactData

class ContactActivity: AppCompatActivity() {
    private lateinit var binding: ContactActivityBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
       setTheme(R.style.Theme_MisantlaAppNoBar)
       super.onCreate(savedInstanceState)
       binding = ContactActivityBinding.inflate(layoutInflater)
       val view = binding.root
       setContentView(view)

       binding.toolbar.setNavigationOnClickListener {
           finish()
       }

        getData()
    }

    private fun getData(){
        db.collection("dataApp").get().addOnSuccessListener { result ->
            for (document in result) {
                val contactData = ContactData(
                    whatsapp = document.data["whatsapp"].toString(),
                    telefono = document.data["numeroReporte"].toString()
                )

                setData(contactData)

                break
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore_", "Error al optener datos_$exception")
        }
    }

    private fun setData(contactData: ContactData){
        if(contactData.whatsapp != ""){
            binding.btnWapp.visibility = View.VISIBLE
            binding.btnWapp.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=52${contactData.whatsapp}")
                )
                startActivity(webIntent)
            }
        }
        if(contactData.telefono != ""){
            binding.btnPhone.visibility = View.VISIBLE
            binding.btnPhone.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:${contactData.telefono}")
                )
                startActivity(callIntent)
            }
        }
    }
}