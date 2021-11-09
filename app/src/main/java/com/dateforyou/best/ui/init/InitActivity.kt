package com.dateforyou.best.ui.init

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dateforyou.best.R
import com.dateforyou.best.ui.check.CheckActivity
import com.dateforyou.best.ui.splash.MySplashActivity
import com.dateforyou.best.utils.getLastUrl
import com.dateforyou.best.utils.saveLastUrl
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isClientError
import com.github.kittinunf.fuel.core.isServerError
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_init.*
import timber.log.Timber

class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        getState()
    }

    private fun getState() {
        Fuel.get("https://truestrike.ru/sunstra")
            .response { request, response, result ->

                Timber.d("TAG_RESP_5: ${Gson().toJson(response)}")

                loadingProgressBar.visibility = View.GONE

                if (response.isSuccessful) {

                    val resp = response.body().asString("application/json; charset=utf-8")

                    Timber.d("TAG_RESP_1: $resp")

                    if (!resp.isNullOrEmpty()) {

                        Timber.d("TAG_RESP_2")

                        if (getLastUrl(this).isNullOrEmpty() || getLastUrl(this) == "https://google.com") {
                            saveLastUrl(this, resp)
                            Timber.d("TAG_RESP_3")
                        }

                        goToCheckActivity()
                    } else {
                        goToRegistrationActivity()
                    }
                }

                if (response.isServerError || response.isClientError) {
                    val responseObjectType = object : TypeToken<Error>() {}.type
                    val responseObject = Gson().fromJson(
                        response.body().asString("application/json; charset=utf-8"),
                        responseObjectType
                    ) as Error

                    Timber.d("TAG_S_6_ERROR: ${responseObject.toString()}")

                    goToRegistrationActivity()
                }
            }
    }

    private fun goToRegistrationActivity() {
        finishAffinity()
        val myIntent = Intent(this, MySplashActivity::class.java)
        startActivity(myIntent)
        overridePendingTransition(0, 0)
    }

    private fun goToCheckActivity() {
        finishAffinity()
        val myIntent = Intent(this, CheckActivity::class.java)
        startActivity(myIntent)
        overridePendingTransition(0, 0)
    }
}