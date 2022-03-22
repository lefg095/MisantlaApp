package com.lefg095.misantlaapp.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.lefg095.misantlaapp.databinding.FragmentBusinessdetailBinding
import com.lefg095.misantlaapp.databinding.FragmentImgAllScreenBinding
import com.squareup.picasso.Picasso

class ScreenImgFragment: Fragment() {
    private lateinit var binding: FragmentImgAllScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImgAllScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAdds()
        val url_img = arguments?.get("url_img").toString()
        Picasso.get().load(url_img).into(binding.imgAllScreen)
    }

    fun loadAdds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}