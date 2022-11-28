package com.lefg095.misantlaapp.ui.business

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.FragmentBusinessdetailBinding
import com.lefg095.misantlaapp.model.BusinessData
import com.lefg095.misantlaapp.util.FOOD
import com.lefg095.misantlaapp.util.TOURISM
import com.squareup.picasso.Picasso


class BusinessDetailFragment : Fragment() {
    private lateinit var binding: FragmentBusinessdetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessdetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAdds()
        val bundle = arguments
        val businessData = bundle!!.get("businessData") as BusinessData
        val businessType = bundle.get("businessType") as String
        val numberAdmin = bundle.get("numero_reporte") as String
        setData(businessData, businessType, numberAdmin)
    }

    private fun loadAdds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setData(businessData: BusinessData, businessType:String, numberAdmin: String) {
        if (numberAdmin != ""){
            binding.fbReport.visibility = View.VISIBLE
            binding.fbReport.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=52${numberAdmin}")
                )
                startActivity(webIntent)
            }
        }
        Picasso.get().load(businessData.url_img).into(binding.imgBusinessDetail)
        binding.tvTitleBusiness.text = businessData.nombre
        if(businessData.ubicacion != "") {
            binding.btnMaps.visibility = View.VISIBLE
            binding.btnMaps.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(businessData.ubicacion)
                )
                startActivity(webIntent)
            }
        }
        binding.imgBusinessDetail.setOnClickListener {
            val businessBundle = bundleOf(
                "url_img" to businessData.url_img
            )
            view?.findNavController()?.navigate(R.id.imgScreen, businessBundle)
        }
        if (businessType == TOURISM){
            if(businessData.desLong != ""){
                binding.tvDescLong.visibility = View.VISIBLE
                binding.tvDescLong.text = businessData.desLong
            }
        }
        if(businessData.horario != ""){
            binding.tvHorario.visibility = View.VISIBLE
            binding.tvHorario.text = getString(R.string.timetable_detail_str, businessData.horario)
        }
        if (businessType == FOOD && businessData.servEntrega != "" && businessData.servLocal != ""){
            binding.cardServices.visibility = View.VISIBLE
            when(businessData.servEntrega){
                "1"->{
                    Picasso.get().load(R.drawable.ic_ok).into(binding.imgServiceEntrega)
                }
                "0"->{
                    Picasso.get().load(R.drawable.ic_dont).into(binding.imgServiceEntrega)
                }
            }
            when(businessData.servLocal){
                "1"->{
                    Picasso.get().load(R.drawable.ic_ok).into(binding.imgserviceLocal)
                }
                "0"->{
                    Picasso.get().load(R.drawable.ic_dont).into(binding.imgserviceLocal)
                }
            }
        }
        if(businessData.telefono != ""){
            binding.btnPhone.visibility = View.VISIBLE
            binding.btnPhone.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:${businessData.telefono}")
                )
                startActivity(callIntent)
            }
        }
        if(businessData.whatsapp != ""){
            binding.btnWapp.visibility = View.VISIBLE
            binding.btnWapp.setOnClickListener {
                val webIntent: Intent = Uri.parse("https://api.whatsapp.com/send?phone=52${businessData.whatsapp}").let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                startActivity(webIntent)
            }
        }
        if(businessData.facebook != ""){
            binding.btnFace.visibility = View.VISIBLE
            binding.btnFace.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(businessData.facebook))
                startActivity(webIntent)
            }
        }
        if(businessData.instagram != ""){
            binding.btnInsta.visibility = View.VISIBLE
            binding.btnInsta.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(businessData.instagram)
                )
                startActivity(webIntent)
            }
        }
        if(businessData.catalogo_menu != "") {
            binding.btnMenuCatalogo.visibility = View.VISIBLE
            binding.btnMenuCatalogo.setOnClickListener {
                val businessBundle = bundleOf(
                    "url_img" to businessData.catalogo_menu
                )
                view?.findNavController()?.navigate(R.id.imgScreen, businessBundle)
            }
        }

    }

}