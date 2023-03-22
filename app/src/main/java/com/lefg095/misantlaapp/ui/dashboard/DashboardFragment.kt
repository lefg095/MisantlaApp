package com.lefg095.misantlaapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.FragmentDashboardBinding
import com.lefg095.misantlaapp.model.SliderData
import com.lefg095.misantlaapp.util.*
import com.smarteist.autoimageslider.SliderView

class DashboardFragment : Fragment(){
    private lateinit var binding: FragmentDashboardBinding
    private val db = FirebaseFirestore.getInstance()

    var urlImg = ""
    var nameBusiness = ""
    var dateEnd = ""
    var dateIni = ""
    var urlBusiness = ""
    var imgUrlDefault = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAdds()

        val sliderDataArrayList: ArrayList<SliderData> = arrayListOf()
        db.collection("Marquesina").get().addOnSuccessListener { result ->
            for (document in result) {
                dateEnd = "${document.data["date_end"]}"
                dateIni = "${document.data["date_ini"]}"
                urlBusiness = "${document.data["url_business"]}"
                urlImg = "${document.data["url_Img"]}"
                nameBusiness = "${document.data["name_business"]}"
                imgUrlDefault = "${document.data["url_default"]}"
                sliderDataArrayList.add(
                    SliderData(
                        dateEnd = dateEnd,
                        dateInit = dateIni,
                        businessUrl = urlBusiness,
                        imgUrl = urlImg,
                        nameBusiness = nameBusiness,
                        imgUrlDefault = imgUrlDefault
                    )
                )
            }
            if (sliderDataArrayList.isNotEmpty()) {
                initSlider(sliderDataArrayList)
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore_", "Error al optener datos_$exception")
        }

        binding.btn1.setOnClickListener {
            showListBusiness(TOURISM)
        }
        binding.btn2.setOnClickListener {
            showListBusiness(FOOD)
        }
        binding.btn3.setOnClickListener {
            showListBusiness(BOUTIQUES)
        }
        binding.btn4.setOnClickListener {
            showListBusiness(GROCERIES)
        }
        binding.btn5.setOnClickListener {
            showListBusiness(SCHOOLS)
        }
        binding.btn6.setOnClickListener {
            showListBusiness(SERVICES)
        }

    }

    private fun loadAdds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun initSlider(sliderDataArrayList: ArrayList<SliderData>) {
        val adapter = SliderAdapter(sliderDataArrayList)
        binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding.slider.setSliderAdapter(adapter)
        binding.slider.scrollTimeInSec = 3
        binding.slider.isAutoCycle = true
        binding.slider.startAutoCycle()
    }

    private fun showListBusiness(businessType: String) {
        val businessTypeBundle = bundleOf("businessType" to businessType)
        view?.findNavController()?.navigate(R.id.listBusiness, businessTypeBundle)
    }
}