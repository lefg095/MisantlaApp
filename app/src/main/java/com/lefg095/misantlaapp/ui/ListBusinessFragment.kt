package com.lefg095.misantlaapp.ui

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
    }

    fun getData(businessType: String) {
        val businessDataArrayList: ArrayList<BusinessData> = arrayListOf()

        db.collection(businessType).get().addOnSuccessListener { result ->
            for (documento in result) {
                businessDataArrayList.add(
                    BusinessData(
                        descripcion = "${documento.data.get("descripcion")}",
                        nombre = "${documento.data.get("nombre")}",
                        ubicacion = "${documento.data.get("ubicacion")}",
                        url_img = "${documento.data.get("url_img")}"
                    )
                )
            }
            if (businessDataArrayList.isNotEmpty()) {
                initRecyclerViewBusiness(businessDataArrayList)
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore_${businessType}_", "Error al optener datos")
        }
    }

    private fun initRecyclerViewBusiness(businnesArrayList: ArrayList<BusinessData>) {
        binding.rvBusinnessList.layoutManager = LinearLayoutManager(requireContext())
        adapter = BusinessAdapter(businnesArrayList)
        binding.rvBusinnessList.adapter = adapter
    }
}