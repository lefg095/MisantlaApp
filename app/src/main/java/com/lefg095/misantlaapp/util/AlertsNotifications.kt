package com.lefg095.misantlaapp.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.Toast
import com.lefg095.misantlaapp.R


class AlertsNotifications {
    fun alertNormal(context: Context){
        val builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = LayoutInflater.from(context);

        builder//.setView(inflater.inflate(R.layout.normal_dialog, null))//cambiar el layout del builder por alguno personalizado
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(context, "Prees OK", Toast.LENGTH_LONG).show()
            })
            .setNegativeButton("CANCELAR", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(context, "Prees CANCELAR", Toast.LENGTH_LONG).show()
            })
            .setTitle("Presione una opcion")
        val dialog = builder.create()
        dialog.show()

        val btn_ok = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        btn_ok.setTextColor(context.resources.getColor(R.color.green))
        val btn_cancelar = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        btn_cancelar.setTextColor(context.resources.getColor(R.color.red))

    }

}