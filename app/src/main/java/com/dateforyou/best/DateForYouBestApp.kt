package com.dateforyou.best

import android.app.Application
import timber.log.Timber

class DateForYouBestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        timber()
    }

    private fun timber() {
        Timber.plant(Timber.DebugTree())
    }
}