package com.lefg095.misantlaapp.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lefg095.misantlaapp.R
import com.lefg095.misantlaapp.databinding.ItemBtnPlaceBinding
import com.squareup.picasso.Picasso

class ButtonsPlacesAdapter(
    val placesList: List<String>,
    val context: Context
): RecyclerView.Adapter<ButtonsPlacesAdapter.ButtonsPlacesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonsPlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ButtonsPlacesViewHolder(layoutInflater.inflate(R.layout.item_btn_place, parent, false))
    }

    override fun onBindViewHolder(holder: ButtonsPlacesViewHolder, position: Int) {
        val place = placesList[position]
        holder.bind(place, place, context)
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    class ButtonsPlacesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemBtnPlaceBinding.bind(view)
        fun bind(place: String, businessType: String, context: Context){
            binding.tvBtnGeneric.text = place.uppercase()
            Picasso.get().load(getLocalImg(place, context)).into(binding.ivBtnGeneric)
            binding.ivBtnGeneric.setOnClickListener {
                val businessTypeBundle = bundleOf("businessType" to businessType)
                itemView.findNavController().navigate(R.id.listBusiness, businessTypeBundle)
            }
        }

        fun getLocalImg(place: String, context: Context): Int {
            return when(place.trim()){
                "hoteles" -> R.drawable.hotel
                "servicios" -> R.drawable.service
                "abarrotes" -> R.drawable.groceries
                "turismo" -> R.drawable.turismo2
                "escuelas" -> R.drawable.escuelas1
                "comida" -> R.drawable.comida2
                "boutiques"-> R.drawable.boutique3
                else -> R.drawable.placewf64
            }
        }
    }
}