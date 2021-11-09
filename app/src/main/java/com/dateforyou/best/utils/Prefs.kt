package com.dateforyou.best.utils

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dateforyou.best.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

fun setGender(context: Context, boolean: Boolean) {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putBoolean(Constants.Prefs.PREFS_IS_GENDER_SET, boolean)
        commit()
    }
}

fun isGenderSet(context: Context): Boolean {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(Constants.Prefs.PREFS_IS_GENDER_SET, false)
}

fun setSettings(context: Context, boolean: Boolean) {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putBoolean(Constants.Prefs.PREFS_IS_SETTINGS_SET, boolean)
        commit()
    }
}

fun isSettingsSet(context: Context): Boolean {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(Constants.Prefs.PREFS_IS_SETTINGS_SET, false)
}

fun setRegistered(context: Context, boolean: Boolean) {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putBoolean(Constants.Prefs.PREFS_IS_REGISTERED, boolean)
        commit()
    }
}

fun isRegistered(context: Context): Boolean {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(Constants.Prefs.PREFS_IS_REGISTERED, false)
}

fun saveLastUrl(context: Context, url: String?) {
    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putString(Constants.Prefs.PREFS_URL, url)
        commit()
    }
}

fun getLastUrl(context: Context): String {
    val sharedPref = context.getSharedPreferences(Constants.Prefs.PREFS_SETTINGS, Context.MODE_PRIVATE)
    val string = sharedPref.getString(Constants.Prefs.PREFS_URL, "https://google.com")
    return string ?: "https://google.com"
}

fun validatePermissions(
    context: Context,
    activity: AppCompatActivity
) {
    Dexter.withActivity(activity)
        .withPermissions(
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        .withListener(object : PermissionListener, MultiplePermissionsListener {
            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
                AlertDialog.Builder(activity)
                    .setTitle(
                        R.string.storage_permission_rationale_title
                    )
                    .setMessage(
                        R.string.storage_permission_rationale_message
                    )
                    .setNegativeButton(
                        android.R.string.cancel
                    ) { dialog, _ ->
                        dialog.dismiss()
                        token?.cancelPermissionRequest()
                    }
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dialog, _ ->
                        dialog.dismiss()
                        token?.continuePermissionRequest()
                    }
                    .setOnDismissListener {
                        token?.cancelPermissionRequest()
                    }
                    .show()
            }

            override fun onPermissionGranted(response: PermissionGrantedResponse?) {

            }

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?, token: PermissionToken?
            ) {
                AlertDialog.Builder(activity)
                    .setTitle(
                        R.string.storage_permission_rationale_title
                    )
                    .setMessage(
                        R.string.storage_permission_rationale_message
                    )
                    .setNegativeButton(
                        android.R.string.cancel
                    ) { dialog, _ ->
                        dialog.dismiss()
                        token?.cancelPermissionRequest()
                    }
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dialog, _ ->
                        dialog.dismiss()
                        token?.continuePermissionRequest()
                    }
                    .setOnDismissListener {
                        token?.cancelPermissionRequest()
                    }
                    .show()
            }

            override fun onPermissionDenied(
                response: PermissionDeniedResponse?
            ) {
            }
        })
        .check()
}