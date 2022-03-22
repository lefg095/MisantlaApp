package com.lefg095.misantlaapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.ItemSliderBinding
import com.lefg095.misantlaapp.model.SliderData
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(
    val mSliderItems: List<SliderData>
) : SliderViewAdapter<SliderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider, parent, false)
        return Holder(inflate)
    }

    override fun onBindViewHolder(viewHolder: Holder, position: Int) {
        val slider = mSliderItems[position]
        viewHolder.bind(slider)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    class Holder(view: View) : SliderViewAdapter.ViewHolder(view) {
        private val binding = ItemSliderBinding.bind(view)
        fun bind(slider: SliderData){
            Picasso.get().load(slider.imgUrl).into(binding.myimage)
        }
    }
}
