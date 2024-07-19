package com.example.weatherforecasts.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        requireActivity(),
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}