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

    fun getData(){
        db.collection("dataApp").get().addOnSuccessListener { result ->
            for (documento in result) {
                val contactData = ContactData(
                    whatsapp = documento.data["whatsapp"].toString(),
                    telefono = documento.data["numeroReporte"].toString()
                )

                setData(contactData)

                break
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore_", "Error al optener datos")
        }
    }

    fun setData(contactData: ContactData){
        if(contactData.whatsapp != ""){
            binding.btnWapp.visibility = View.VISIBLE
            binding.btnWapp.setOnClickListener {
                val webIntent: Intent = Uri.parse("https://api.whatsapp.com/send?phone=52${contactData.whatsapp}").let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                startActivity(webIntent)
            }
        }
        if(contactData.telefono != ""){
            binding.btnPhone.visibility = View.VISIBLE
            binding.btnPhone.setOnClickListener {
                val callIntent: Intent = Uri.parse("tel:${contactData.telefono}").let { number ->
                    Intent(Intent.ACTION_DIAL, number)
                }
                startActivity(callIntent)
            }
        }
    }
}