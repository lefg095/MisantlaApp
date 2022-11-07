package com.lefg095.misantlaapp.ui.business.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactData(
    val telefono: String? = "",
    val whatsapp: String? = "",
    val facebook: String? = "",
    val instagram: String? = "",
): Parcelable
