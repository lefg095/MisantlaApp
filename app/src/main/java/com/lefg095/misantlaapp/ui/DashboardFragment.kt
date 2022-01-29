package com.lefg095.misantlaapp.ui

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
import com.smarteist.autoimageslider.SliderView

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val db = FirebaseFirestore.getInstance()

    // Urls of our images.
    val url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png"
    val url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp"
    val url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png"
    var url = ""
    var nameBusiness = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAdds()

        val sliderDataArrayList: ArrayList<SliderData> = arrayListOf()
        var datos = ""
        db.collection("Marquesina").get().addOnSuccessListener { result ->
            for (documento in result) {
                datos += "${documento.data.get("url_Img")}\n\n"
                url = "${documento.data.get("url_Img")}"
                nameBusiness = "${documento.data.get("name_business")}"
                sliderDataArrayList.add(
                    SliderData(
                        imgUrl = url,
                        nameBusiness = nameBusiness
                    )
                )
            }
            if (sliderDataArrayList.isNotEmpty()) {
                initSlider(sliderDataArrayList)
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore_", "Error al optener datos")
        }

        binding.btn1.setOnClickListener {
            showListBusiness("turismo")
        }

    }

    fun loadAdds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    fun initSlider(sliderDataArrayList: ArrayList<SliderData>) {
        val adapter = SliderAdapter(sliderDataArrayList)
        binding.slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR)
        binding.slider.setSliderAdapter(adapter)
        binding.slider.setScrollTimeInSec(3)
        binding.slider.setAutoCycle(true)
        binding.slider.startAutoCycle()
    }

    fun showListBusiness(businessType: String) {
        val businessTypeBundle = bundleOf("businessType" to businessType)
        view?.findNavController()?.navigate(R.id.listBusiness, businessTypeBundle)
    }
}