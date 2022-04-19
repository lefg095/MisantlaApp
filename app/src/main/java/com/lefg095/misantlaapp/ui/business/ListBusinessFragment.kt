package com.lefg095.misantlaapp.ui.business

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.lefg095.misantlaapp.databinding.FragmentListbusinessBinding
import com.lefg095.misantlaapp.model.BusinessData
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

        db.collection(businessType).get().addOnSuccessListener { result ->
            for (documento in result) {
                businessDataArrayList.add(
                    BusinessData(
                        descripcion = "${documento.data.get("descripcion")}",
                        nombre = (documento.data.get("nombre")?: "").toString(),
                        ubicacion = (documento.data.get("ubicacion")?: "").toString(),
                        url_img = (documento.data.get("url_img")?: "").toString(),
                        desLong = (documento.data.get("descLong")?: "").toString(),
                        horario = (documento.data.get("horario")?: "").toString(),
                        telefono = (documento.data.get("telefono")?: "").toString(),
                        whatsapp = (documento.data.get("whatsapp")?: "").toString(),
                        facebook = (documento.data.get("facebook")?: "").toString(),
                        instagram = (documento.data.get("instagram")?: "").toString(),
                        servLocal = (documento.data.get("servLocal")?: "").toString(),
                        servEntrega = (documento.data.get("servEntrega")?: "").toString(),
                        catalogo_menu = (documento.data.get("productos")?: "").toString()
                    )
                )
            }
            if (businessDataArrayList.isNotEmpty()) {
                initRecyclerViewBusiness(businessDataArrayList, businessType)
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

    private fun initRecyclerViewBusiness(businnesArrayList: ArrayList<BusinessData>, businessType: String) {
        binding.rvBusinnessList.layoutManager = LinearLayoutManager(requireContext())
        adapter = BusinessAdapter(businnesArrayList, businessType)
        binding.rvBusinnessList.adapter = adapter
        hideLoading(3.0)
    }
}