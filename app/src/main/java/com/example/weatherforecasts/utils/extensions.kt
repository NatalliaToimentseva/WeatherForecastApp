package com.example.weatherforecasts.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(permissions: List<String>): Boolean {
    var denied = 0
    for (p in permissions) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                p
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            denied++
        }
    }
    return denied == 0
}

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}