package com.lefg095.misantlaapp.ui.business

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import com.lefg095.misantlaapp.databinding.FragmentReportBinding
import com.lefg095.misantlaapp.util.*

class ReportFragment: Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentReportBinding
    var nombre = ""
    var descReport = ""
    var numberPhone = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddSsOrImg.setOnClickListener {
            fileUpload()
        }

        binding.btnSendReport.setOnClickListener {
            sendReport()
        }

    }

    fun sendReport(){
        if (validateFields()){
            if (!binding.btnAddSsOrImg.isVisible){
                db.collection("reportes")
                    .document(nombre.cleanText())
                    .set(getData()
                    ).addOnSuccessListener {
                        alertSuccessSendData()
                    }.addOnFailureListener {
                        alertError(
                            context = requireContext(),
                            message = "Ocurrio algun problema al realizar el reporte!"
                        )
                    }
            } else {
                alertWarning(
                    context = requireContext(),
                    title = "Alerta!",
                    message = "Faltan agregar una imagen"
                )
            }
        }
    }

    private fun alertSuccessSendData() {
        val builder = AlertDialog.Builder(context)
        builder
            .setIcon(R.drawable.ic_ok)
            .setPositiveButton("OK") { _, _ ->
                view?.findNavController()?.navigate(R.id.action_pop_out_of_game)            }
            .setTitle("Exito!")
            .setMessage("Se hizo el reporte correctamente!")
        val dialog = builder.create()
        dialog.show()

        val btnOk = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        btnOk.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
    }

    private fun getData(): HashMap<String, Any> {
        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs?.getString("email", "") ?: ""
        return hashMapOf(
                    "atendido" to "0",
                    "nombre" to nombre,
                    "correo" to email,
                    "descriptionReport" to descReport
                )
    }

    private fun fileUpload(){
        if (validateFields()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE){
            if (resultCode == Activity.RESULT_OK){
                savePicture(data)
            }
        }
    }

    private fun savePicture(data: Intent?) {
        val fileUri = data?.data
        val folder: StorageReference = FirebaseStorage
            .getInstance()
            .reference
            .child("subidas/reportes/${nombre.cleanText()}")
            .child(fileUri?.lastPathSegment!!)

        fileUri.let {
            folder.putFile(it)
                .addOnSuccessListener{
                    binding.btnAddSsOrImg.visibility = View.GONE
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

    private fun validateFields(): Boolean{
        nombre = binding.etName.text.toString()
        numberPhone = binding.etPhone.text.toString()
        descReport = binding.etReportDescription.text.toString()

        return when{
            !nombre.validateText() -> {
                binding.ilName.error = "Escribe un nombre"
                binding.etName.requestFocus()
                binding.etName.doOnTextChanged { _, _, _, _ ->
                    binding.ilName.error = null
                }
                false
            }
            !numberPhone.validateText() -> {
                binding.ilPhone.error = "Escribe un número de telefono"
                binding.etPhone.requestFocus()
                binding.etPhone.doOnTextChanged { _, _, _, _ ->
                    binding.ilPhone.error = null
                }
                false
            }
            !descReport.validateText() -> {
                binding.ilReportDescription.error = "Describe tu reporte"
                binding.etReportDescription.requestFocus()
                binding.etPhone.doOnTextChanged { _, _, _, _ ->
                    binding.ilReportDescription.error = null
                }
                false
            }
            else -> {
                true
            }
        }
    }

}