package com.lefg095.misantlaapp.ui.business

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.lefg095.misantlaapp.databinding.FragmentAddPlacesBinding

class AddPlacesFragment: Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentAddPlacesBinding
    private val FILE = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val businessType = arguments?.get("businessType").toString()
        binding.btnAgregar.setOnClickListener {
            saveData(businessType)
        }

        binding.btnAgregarImagen.setOnClickListener {
            fileUpluoad()
        }
    }

    fun fileUpluoad(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, FILE)
    }

    fun saveData(businessType: String){
        val nombre = binding.etNombre.text
        val longDesc = binding.etLongDesc.text
        val numCalling = binding.etNumCalling.text
        val numContacto = binding.etNumContacto.text
        val numWhatsapp = binding.etNumWhatsapp.text
        val shotDesc = binding.etShortDesc.text
        val location = binding.etUbicacion.text
        val facebook = binding.etUrlFacebook.text
        val instagram = binding.etUrlInstagra.text
        val servEntrega = ""
        val servLocal = ""
        val urlImg = ""
        val horario = ""
        val productos = ""
        db.collection("agregaciones").document("prueba02").set(
            hashMapOf("campoPrueba" to "datoPrueba")
        )
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE){
            if (resultCode ==  RESULT_OK){
                val FileUri = data!!.data
                val Folder: StorageReference = FirebaseStorage.getInstance().getReference().child("usuarios")
            }
        }
    }*/
}