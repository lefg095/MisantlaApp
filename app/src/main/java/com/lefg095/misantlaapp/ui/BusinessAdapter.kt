package com.lefg095.misantlaapp.ui

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.ItemBussineBinding
import com.lefg095.misantlaapp.model.BusinessData
import com.squareup.picasso.Picasso

class BusinessAdapter(
    val businnesList: ArrayList<BusinessData>
): RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BusinessViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BusinessViewHolder(layoutInflater.inflate(R.layout.item_bussine, parent, false))
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = businnesList[position]
        holder.bind(business)
    }

    override fun getItemCount(): Int {
        return businnesList.size
    }

    class BusinessViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding= ItemBussineBinding.bind(view)
            fun bind(business: BusinessData){
                Picasso.get().load(business.url_img).into(binding.imgBusiness)
                binding.tvBusinessName.text = business.nombre
                binding.tvBusinessDesc.text = business.descripcion
                binding.cardBusiness.setOnClickListener {
                    var businessTypeBundle = bundleOf("businessData" to business)
                    itemView?.findNavController()?.navigate(R.id.detailBusiness, businessTypeBundle)
                }
            }
    }
}