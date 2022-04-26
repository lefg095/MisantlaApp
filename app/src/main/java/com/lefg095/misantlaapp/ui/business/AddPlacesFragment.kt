package com.lefg095.misantlaapp.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.lefg095.misantlaapp.databinding.FragmentAddPlacesBinding

class AddPlacesFragment: Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentAddPlacesBinding

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
}