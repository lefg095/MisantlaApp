package com.lefg095.misantlaapp.ui.business

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.FragmentListbusinessBinding
import com.lefg095.misantlaapp.model.BusinessData
import com.lefg095.misantlaapp.ui.business.adapter.BusinessAdapter
import com.lefg095.misantlaapp.util.alertError
import com.lefg095.misantlaapp.util.alertWarning

class ListBusinessFragment : Fragment() {
    private lateinit var binding: FragmentListbusinessBinding
    private val db = FirebaseFirestore.getInstance()
    private var adapter: BusinessAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListbusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val businessType = arguments?.get("businessType").toString()
        getData(businessType)
        showLoading()
    }

    private fun getData(businessType: String) {
        val businessDataArrayList: ArrayList<BusinessData> = arrayListOf()
        var numberAdmin = ""
        db.collection("dataApp").get().addOnSuccessListener { result ->
            for (document in result) {
                numberAdmin = "${document.data["numeroReporte"]}"
            }
        }.addOnFailureListener {
            Log.e("Firestore_dataApp_", "Error al optener datos")
        }
        db.collection(businessType).get().addOnSuccessListener { result ->
            for (documento in result) {
                val showData = documento.data["mostrar"]
                if (showData == "1") {
                    businessDataArrayList.add(
                        BusinessData(
                            descripcion = "${documento.data["descripcion"]}",
                            nombre = (documento.data["nombre"] ?: "").toString(),
                            ubicacion = (documento.data["ubicacion"] ?: "").toString(),
                            url_img = (documento.data["url_img"] ?: "").toString(),
                            desLong = (documento.data["descLong"] ?: "").toString(),
                            horario = (documento.data["horario"] ?: "").toString(),
                            telefono = (documento.data["telefono"] ?: "").toString(),
                            whatsapp = (documento.data["whatsapp"] ?: "").toString(),
                            facebook = (documento.data["facebook"] ?: "").toString(),
                            instagram = (documento.data["instagram"] ?: "").toString(),
                            servLocal = (documento.data["servLocal"] ?: "").toString(),
                            servEntrega = (documento.data["servEntrega"] ?: "").toString(),
                            catalogo_menu = (documento.data["productos"] ?: "").toString(),
                        )
                    )
                }
            }
            if (businessDataArrayList.isNotEmpty()) {
                initRecyclerViewBusiness(businessDataArrayList, businessType)
            }else{
                hideLoading(0.5)
                alertWarning(requireContext(), "Sin lugares!", "No se encontraron lugares para mostrar!")
                binding.tvListVacia.visibility = View.VISIBLE
                binding.rvBusinnessList.visibility = View.GONE
            }
        }.addOnFailureListener {
            alertError(requireContext(), "Error al optener datos!")
            parentFragmentManager.popBackStack()
            Log.e("Firestore_${businessType}_", "Error al optener datos")
        }
    }

    private fun showLoading(){
        binding.imgLoading.visibility = View.VISIBLE
    }

    private fun hideLoading(time: Double){
        val times = time*1000
        try {
            Thread.sleep(times.toLong())
            binding.imgLoading.visibility = View.GONE
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun initRecyclerViewBusiness(businnesArrayList: ArrayList<BusinessData>, businessType: String) {
        binding.rvBusinnessList.layoutManager = LinearLayoutManager(requireContext())
        adapter = BusinessAdapter(businnesArrayList, businessType)
        binding.rvBusinnessList.adapter = adapter
        binding.fbAdd.visibility = View.VISIBLE
        binding.fbAdd.setOnClickListener {
            val businessBundle = bundleOf(
                "businessType" to businessType,
                "listPlaces" to businnesArrayList
            )
            view?.findNavController()?.navigate(R.id.add_places, businessBundle)
        }
        hideLoading(3.0)
    }
}