package com.lefg095.misantlaapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lefg095.misantlaapp.databinding.FragmentBusinessdetailBinding
import com.lefg095.misantlaapp.model.BusinessData
import com.squareup.picasso.Picasso


class BusinessDetailFragment : Fragment() {
    private lateinit var binding: FragmentBusinessdetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessdetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = arguments
        val businessData = bundle!!.get("businessData") as BusinessData
        //val businessData: BusinessData = arguments?.get("businessData") as BusinessData
        setData(businessData)
    }

    private fun setData(businessData: BusinessData) {
        Picasso.get().load(businessData.url_img).into(binding.imgBusinessDetail)
        binding.tvTitleBusiness.text = businessData.nombre
        binding.btnMaps.setOnClickListener {
            val webIntent: Intent = Uri.parse(businessData.ubicacion).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent)
        }

        
    }

}