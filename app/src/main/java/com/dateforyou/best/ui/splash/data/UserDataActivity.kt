package com.dateforyou.best.ui.splash.data

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.dateforyou.best.R
import com.dateforyou.best.ui.splash.gender.GenderActivity
import com.dateforyou.best.utils.hideKeyboard
import com.dateforyou.best.utils.setSettings
import com.dateforyou.best.utils.showErrorSnackbar
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_user_data.*
import java.io.File

class UserDataActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, UserDataActivity::class.java)
        }
    }

    lateinit var imagePickerAvatarLauncher: ImagePickerLauncher

    private var isAvatarPicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        registerPicker()
        setOnClickListener()
    }

    private fun registerPicker() {
        imagePickerAvatarLauncher = registerImagePicker { result: List<Image> ->
            if (result.isEmpty()) return@registerImagePicker
            val path = result[0].path
            Glide.with(this)
                .load(File(path))
                .circleCrop()
                .into(iv_photo)

            tv_plus_sign?.visibility = View.GONE

            isAvatarPicked = true
        }
    }

    private fun setOnClickListener() {
        iv_next?.setOnClickListener {
            onNextClicked()
        }
        iv_photo?.setOnClickListener {
            checkPermissions()
        }
    }

    private fun onNextClicked() {
        hideKeyboard(this, et_name, cl_user_data)

        if (et_name?.text.toString().isEmpty()) {
            showErrorSnackbar(this, cl_user_data, getString(R.string.error_name_empty))
            return
        }

        if(!isAvatarPicked) {
            showErrorSnackbar(this, cl_user_data, getString(R.string.error_avatar_empty))
            return
        }

        saveUser()
    }

    private fun saveUser() {
        setSettings(this, true)
        startActivity(GenderActivity.newIntent(this))
    }

    private fun launchAvatarPicker() {
        imagePickerAvatarLauncher.launch(ImagePickerConfig {
            mode = ImagePickerMode.SINGLE
        })
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            launchAvatarPicker()
        } else {
            validatePermissions()
        }
    }

    private fun validatePermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .withListener(object : PermissionListener, MultiplePermissionsListener {
                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    launchAvatarPicker()
                }

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (ActivityCompat.checkSelfPermission(
                            this@UserDataActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            this@UserDataActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            this@UserDataActivity,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        launchAvatarPicker()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?, token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(
                    response: PermissionDeniedResponse?
                ) { }
            })
            .check()
    }
}