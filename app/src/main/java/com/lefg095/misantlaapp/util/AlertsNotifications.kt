package com.lefg095.misantlaapp.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.core.content.ContextCompat
import com.lefg095.misantlaapp.R


fun alertError(context: Context, message: String) {
    val builder = AlertDialog.Builder(context)

    builder
        .setIcon(R.drawable.ic_dont)
        .setPositiveButton("OK") { _, _ -> }
        .setTitle("Error")
        .setMessage(message)
    val dialog = builder.create()
    dialog.show()

    val btnOk = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
    btnOk.setTextColor(ContextCompat.getColor(context, R.color.red))

}

fun alertSucces(context: Context, message: String) {
    val builder = AlertDialog.Builder(context)

    builder
        .setIcon(R.drawable.ic_ok)
        .setPositiveButton("OK") { _, _ -> }
        .setTitle("Exito!")
        .setMessage(message)
    val dialog = builder.create()
    dialog.show()

    val btnOk = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
    btnOk.setTextColor(ContextCompat.getColor(context, R.color.red))

}

fun alertWarning(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)

    builder
        .setIcon(R.drawable.ic_alert)
        .setPositiveButton("OK") { _, _ -> }
        .setTitle(title)
        .setMessage(message)
    val dialog = builder.create()
    dialog.show()

    val btnOk = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
    btnOk.setTextColor(ContextCompat.getColor(context, R.color.orange))

}

fun alertAuth(context: Context) {
    val builder = AlertDialog.Builder(context)

    builder
        .setIcon(R.drawable.ic_dont)
        .setPositiveButton("OK") { _, _ ->
        }
        .setTitle("Error")
        .setMessage("Se ha producido un error autenticando al usuario" )
    val dialog = builder.create()
    dialog.show()

    val btnOk = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
    btnOk.setTextColor(ContextCompat.getColor(context, R.color.red))

}
