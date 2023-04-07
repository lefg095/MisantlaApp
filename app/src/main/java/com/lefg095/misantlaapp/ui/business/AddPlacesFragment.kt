package com.lefg095.misantlaapp.ui.business

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.FragmentAddPlacesBinding
import com.lefg095.misantlaapp.model.BusinessData
import com.lefg095.misantlaapp.util.*

class AddPlacesFragment: Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentAddPlacesBinding
    var listPlaces: ArrayList<BusinessData> = arrayListOf()
    private var businessType = ""
    var imgType = ""
    private var nombre = ""
    private var shortDesc = ""
    private var longDesc = ""
    private var location = ""
    private var facebook = ""
    private var instagram = ""
    private var numCalling = ""
    private var numWhatsapp = ""
    private var numContacto = ""
    private var servEntrega = ""
    private var servLocal = ""
    private var urlImg = ""
    private var horario = ""
    private var nameContact = ""
    private var productos = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        businessType = arguments?.get("businessType").toString()
        listPlaces = arguments?.getParcelableArrayList("listPlaces")!!
        binding.btnAgregar.setOnClickListener {
            saveData()
        }
        binding.btnAddImgLogo.setOnClickListener {
            imgType = "logo"
            fileUpload()
        }
        binding.btnAddImgMenuProducts.setOnClickListener {
            imgType = "menu"
            fileUpload()
        }
        showFields()
    }

    private fun fileUpload(){
        nombre = binding.etNombre.text.toString()
        if (searchData() && validateFields()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, FILE)
        }
    }

    private fun saveData(){

        if (searchData()){
            if (validateFields()) {
                if (!binding.btnAddImgLogo.isVisible &&
                    (!binding.btnAddImgMenuProducts.isVisible)
                ) {
                    db.collection(businessType)
                        .document(nombre.cleanText())
                        .set(getData()
                    ).addOnSuccessListener {
                        saveFinalData()
                    }.addOnFailureListener {
                        showErrorUpData()
                    }
                }else {
                    alertWarning(
                        context = requireContext(),
                        title = "Alerta!",
                        message = "Faltan imagenes por agregar"
                    )
                }
            }
        }
    }

    private fun saveFinalData(){
        db.collection("agregaciones").document(nombre.cleanText()).set(
            hashMapOf(
                "nombre" to nombre,
                "contacto" to numContacto,
                "name_contacto" to nameContact,
                "categoria" to businessType
            )
        ).addOnSuccessListener {
            alertSuccessSendData()
        }.addOnFailureListener {
            showErrorUpData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE){
            if (resultCode ==  RESULT_OK){
                savePicture(data)
            }
        }
    }

    private fun savePicture(data: Intent?) {
        val fileUri = data?.data
        val folder: StorageReference = FirebaseStorage
            .getInstance()
            .reference
            .child("subidas/$businessType/${nombre.cleanText()}/$imgType")
            .child(fileUri?.lastPathSegment!!)

        fileUri.let {
            folder.putFile(it)
                .addOnSuccessListener{
                    when(imgType){
                        "logo" -> {
                            binding.btnAddImgLogo.visibility = View.GONE
                        }
                        "menu" -> {
                            binding.btnAddImgMenuProducts.visibility = View.GONE
                        }
                    }
                    imgType = ""
                    Toast.makeText(requireContext(), "Imagen subida correctamente", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    alertError(
                        context = requireContext(),
                        message = "Error al subir imagen.\n" +
                                "Contacte a administración."
                    )
                }
        }
    }

    private fun getData(): HashMap<String, Any> {
        return when(businessType) {
            TOURISM -> {
                hashMapOf(
                    "mostrar" to "0",
                    "nombre" to nombre,
                    "descripcion" to shortDesc,
                    "descLong" to longDesc,
                    "ubicacion" to location,
                    "url_img" to urlImg
                )
            }
            SCHOOLS -> {
                hashMapOf(
                    "descripcion" to shortDesc,
                    "facebook" to facebook,
                    "horario" to horario,
                    "mostrar" to "0",
                    "nombre" to nombre,
                    "telefono" to numCalling,
                    "ubicacion" to location,
                    "url_img" to urlImg,
                    "whatsapp" to numWhatsapp,
                    "instagram" to instagram,
                    "productos" to productos
                )
            }
            GROCERIES, BOUTIQUES, FOOD, SERVICES -> {
                hashMapOf(
                    "descripcion" to shortDesc,
                    "facebook" to facebook,
                    "horario" to horario,
                    "mostrar" to "0",
                    "nombre" to nombre,
                    "servEntrega" to servEntrega,
                    "servLocal" to servLocal,
                    "telefono" to numCalling,
                    "ubicacion" to location,
                    "url_img" to urlImg,
                    "whatsapp" to numWhatsapp,
                    "instagram" to instagram,
                    "productos" to productos
                )
            }
            else -> {
                hashMapOf()
            }
        }
    }

    private fun validateFields(): Boolean{
        nombre = binding.etNombre.text.toString()
        shortDesc = binding.etShortDesc.text.toString()
        longDesc = binding.etLongDesc.text.toString()
        location = binding.etUbicacion.text.toString()
        facebook = binding.etUrlFacebook.text.toString()
        instagram = binding.etUrlInstagra.text.toString()
        numCalling = binding.etNumCalling.text.toString()
        numWhatsapp = binding.etNumWhatsapp.text.toString()
        numContacto = binding.etNumContacto.text.toString()
        nameContact = binding.etNombreContacto.text.toString()
        servEntrega = binding.chbServEntrega.isChecked.zeroOrOneChange()
        servLocal = binding.chbServLocal.isChecked.zeroOrOneChange()
        horario = binding.etHorario.text.toString()

        return when{
            !nombre.validateText() -> {
                binding.ilNombre.error = "Escribe un nombre"
                binding.etNombre.requestFocus()
                binding.etNombre.doOnTextChanged { _, _, _, _ ->
                    binding.ilNombre.error = null
                }
                false
            }
            !shortDesc.validateText() -> {
                binding.ilShortDesc.error = "Escribe una descripción corta"
                binding.etShortDesc.requestFocus()
                binding.etShortDesc.doOnTextChanged { _, _, _, _ ->
                    binding.ilShortDesc.error = null
                }
                false
            }
            businessType == TOURISM && !longDesc.validateText() -> {
                binding.ilLongDesc.error = "Escribe una descripción larga"
                binding.etLongDesc.requestFocus()
                binding.etLongDesc.doOnTextChanged { _, _, _, _ ->
                    binding.ilLongDesc.error = null
                }
                false
            }
            (binding.chbServLocal.isChecked && !location.validateText()) ||
                    (businessType == TOURISM && !location.validateText())-> {
                binding.ilUbicacion.error = "Pega aquí la url de la ubicación"
                binding.etUbicacion.requestFocus()
                binding.etUbicacion.doOnTextChanged { _, _, _, _ ->
                    binding.ilUbicacion.error = null
                }
                false
            }
            businessType != TOURISM &&  binding.chbFacebook.isChecked && !facebook.validateText() -> {
                binding.ilUrlFacebook.error = "Pega aquí la url de facebook"
                binding.etUrlFacebook.requestFocus()
                binding.etUrlFacebook.doOnTextChanged { _, _, _, _ ->
                    binding.ilUrlFacebook.error = null
                }
                false
            }
            businessType != TOURISM &&  binding.chbInstagram.isChecked && !instagram.validateText() -> {
                binding.ilUrlInstagra.error = "Pega aquí la url de Instagram"
                binding.etUrlInstagra.requestFocus()
                binding.etUrlInstagra.doOnTextChanged { _, _, _, _ ->
                    binding.ilUrlInstagra.error = null
                }
                false
            }
            businessType != TOURISM && !numCalling.validateText() -> {
                binding.ilNumCalling.error = "Escribe un número para llamadas"
                binding.etNumCalling.requestFocus()
                binding.etNumCalling.doOnTextChanged { _, _, _, _ ->
                    binding.ilNumCalling.error = null
                }
                false
            }
            businessType != TOURISM && binding.chbWhatsapp.isChecked && !numWhatsapp.validateText() -> {
                binding.ilNumWhatsapp.error = "Escribe un número para whatsapp"
                binding.etNumWhatsapp.requestFocus()
                binding.etNumWhatsapp.doOnTextChanged { _, _, _, _ ->
                    binding.ilNumWhatsapp.error = null
                }
                false
            }
            businessType != TOURISM && !horario.validateText() -> {
                binding.ilHorario.error = "Escribe el horario de atención"
                binding.etHorario.requestFocus()
                binding.etHorario.doOnTextChanged { _, _, _, _ ->
                    binding.ilHorario.error = null
                }
                false
            }
            !numContacto.validateText() -> {
                binding.ilNumContacto.error = "Escribe un número para contactarte"
                binding.etNumContacto.requestFocus()
                binding.etNumContacto.doOnTextChanged { _, _, _, _ ->
                    binding.ilNumContacto.error = null
                }
                false
            }
            !nameContact.validateText() -> {
                binding.ilNombreContacto.error = "Escribe tu nombre"
                binding.etNombreContacto.requestFocus()
                binding.etNombreContacto.doOnTextChanged { _, _, _, _ ->
                    binding.ilNombreContacto.error = null
                }
                false
            }
            else -> {
                true
            }
        }
    }

    private fun showFields(){
        binding.chbFacebook.setOnCheckedChangeListener { _, isChecked ->
            binding.ilUrlFacebook.visibility = View.GONE
            if (isChecked){
                binding.ilUrlFacebook.visibility = View.VISIBLE
            }
        }
        binding.chbWhatsapp.setOnCheckedChangeListener { _, isChecked ->
            binding.ilNumWhatsapp.visibility = View.GONE
            if (isChecked){
                binding.ilNumWhatsapp.visibility = View.VISIBLE
            }
        }
        binding.chbInstagram.setOnCheckedChangeListener { _, isChecked ->
            binding.ilUrlInstagra.visibility = View.GONE
            if (isChecked){
                binding.ilUrlInstagra.visibility = View.VISIBLE
            }
        }
        binding.chbServLocal.setOnCheckedChangeListener { _, isChecked ->
            binding.ilUbicacion.visibility = View.GONE
            if (isChecked){
                binding.ilUbicacion.visibility = View.VISIBLE
            }
        }

        when(businessType){
            TOURISM -> {
                binding.ilUbicacion.visibility = View.VISIBLE
                binding.chbFacebook.visibility = View.GONE
                binding.ilHorario.visibility = View.GONE
                binding.chbServEntrega.visibility = View.GONE
                binding.chbServLocal.visibility = View.GONE
                binding.chbInstagram.visibility = View.GONE
                binding.chbWhatsapp.visibility = View.GONE
                binding.ilNumCalling.visibility = View.GONE
                binding.btnAddImgMenuProducts.visibility = View.GONE
            }
            SCHOOLS -> {
                binding.ilLongDesc.visibility = View.GONE
                binding.chbServEntrega.visibility = View.GONE
                binding.chbServLocal.visibility = View.GONE
            }
            else -> {
                binding.ilLongDesc.visibility = View.GONE
            }
        }
    }

    private fun showErrorUpData(){
        alertError(
            context = requireContext(),
            message = "Error al subir datos.\n" +
                    "Contacte a administración."
        )
    }

    private fun alertSuccessSendData() {
        val builder = AlertDialog.Builder(context)
        builder
            .setIcon(R.drawable.ic_ok)
            .setPositiveButton("OK") { _, _ ->
                view?.findNavController()?.navigate(R.id.action_pop_out_of_game)            }
            .setTitle("Exito!")
            .setMessage("Se agregaron los datos correctamente\n" +
                    "Los datos seran verificados por administración antes de ser mostrados a todos")
        val dialog = builder.create()
        dialog.show()

        val btnOk = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        btnOk.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
    }

    private fun searchData(): Boolean {
        return if (nombre != "") {
            val resp = listPlaces.find { nombre.lowercase() == it.nombre?.lowercase() }
             if((resp?.nombre ?: "").isEmpty()) {
                 true
             }else {
                 binding.ilNombre.error = "Escribe un nombre diferente!"
                 binding.etNombre.requestFocus()
                 binding.etNombre.doOnTextChanged { _, _, _, _ ->
                     binding.ilNombre.error = null
                 }
                 alertWarning(
                     context = requireContext(),
                     title = "Alerta!",
                     message = "Este lugar ya existe en la app!"
                 )
                 false
             }
        }else{
            binding.ilNombre.error = "Escribe un nombre"
            binding.etNombre.requestFocus()
            binding.etNombre.doOnTextChanged { _, _, _, _ ->
                binding.ilNombre.error = null
            }
            false
        }
    }
}