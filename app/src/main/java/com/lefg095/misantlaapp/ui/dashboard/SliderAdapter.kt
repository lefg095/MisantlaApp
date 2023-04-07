package com.lefg095.misantlaapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.ItemSliderBinding
import com.lefg095.misantlaapp.model.SliderData
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class SliderAdapter(
    private val mSliderItems: List<SliderData>
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

    class Holder(view: View) : ViewHolder(view) {
        private val binding = ItemSliderBinding.bind(view)
        fun bind(slider: SliderData){
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateToday = Date()
            val dateEndSlide = dateFormat.parse(slider.dateEnd)

            Picasso.get().load(
                if (dateEndSlide.after(dateToday)) {
                    slider.imgUrl
                }else {
                    slider.imgUrlDefault
                }).into(binding.myimage)
        }
    }
}
