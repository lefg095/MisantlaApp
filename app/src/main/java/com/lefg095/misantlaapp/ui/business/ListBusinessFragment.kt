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
    ): View? {
        binding = FragmentListbusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val businessType = arguments?.get("businessType").toString()
        getData(businessType)
        showLoading()
    }

    fun getData(businessType: String) {
        val businessDataArrayList: ArrayList<BusinessData> = arrayListOf()
        var numero_reporte = ""
        db.collection("dataApp").get().addOnSuccessListener { result ->
            Log.e("Firestore_dataApp_", "$result")
            for (documento in result) {
                numero_reporte = "${documento.data.get("numeroReporte")}"
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore_dataApp_", "Error al optener datos")
        }
        db.collection(businessType).get().addOnSuccessListener { result ->
            for (documento in result) {
                val mostrar = documento.data.get("mostrar")
                if (mostrar == "1") {
                    businessDataArrayList.add(
                        BusinessData(
                            descripcion = "${documento.data.get("descripcion")}",
                            nombre = (documento.data.get("nombre") ?: "").toString(),
                            ubicacion = (documento.data.get("ubicacion") ?: "").toString(),
                            url_img = (documento.data.get("url_img") ?: "").toString(),
                            desLong = (documento.data.get("descLong") ?: "").toString(),
                            horario = (documento.data.get("horario") ?: "").toString(),
                            telefono = (documento.data.get("telefono") ?: "").toString(),
                            whatsapp = (documento.data.get("whatsapp") ?: "").toString(),
                            facebook = (documento.data.get("facebook") ?: "").toString(),
                            instagram = (documento.data.get("instagram") ?: "").toString(),
                            servLocal = (documento.data.get("servLocal") ?: "").toString(),
                            servEntrega = (documento.data.get("servEntrega") ?: "").toString(),
                            catalogo_menu = (documento.data.get("productos") ?: "").toString()
                        )
                    )
                }
            }
            if (businessDataArrayList.isNotEmpty()) {
                initRecyclerViewBusiness(businessDataArrayList, businessType, numero_reporte)
            }else{
                hideLoading(0.5)
                alertWarning(requireContext(), "Sin lugares!", "No se encontraron lugares para mostrar!")
                binding.tvListVacia.visibility = View.VISIBLE
                binding.rvBusinnessList.visibility = View.GONE
            }
        }.addOnFailureListener { exception ->
            alertError(requireContext(), "Error al optener datos!")
            parentFragmentManager.popBackStack()
            Log.e("Firestore_${businessType}_", "Error al optener datos")
        }
    }

    fun showLoading(){
        binding.imgLoading.visibility = View.VISIBLE
    }

    fun hideLoading(time: Double){
        var times = time*1000
        try {
            Thread.sleep(times.toLong())
            binding.imgLoading.visibility = View.GONE
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun initRecyclerViewBusiness(businnesArrayList: ArrayList<BusinessData>, businessType: String, numero_reporte: String) {
        binding.rvBusinnessList.layoutManager = LinearLayoutManager(requireContext())
        adapter = BusinessAdapter(businnesArrayList, businessType, numero_reporte)
        binding.rvBusinnessList.adapter = adapter
        binding.fbAdd.visibility = View.VISIBLE
        binding.fbAdd.setOnClickListener {
            val businessBundle = bundleOf(
                "businessType" to businessType
            )
            view?.findNavController()?.navigate(R.id.add_places, businessBundle)
        }
        hideLoading(3.0)
    }
}