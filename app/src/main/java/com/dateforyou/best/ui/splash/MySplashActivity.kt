package com.dateforyou.best.ui.splash


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dateforyou.best.R
import com.dateforyou.best.ui.splash.choose.ChooseActivity
import com.dateforyou.best.ui.splash.data.UserDataActivity
import com.dateforyou.best.ui.splash.gender.GenderActivity
import com.dateforyou.best.ui.splash.register.RegisterActivity
import com.dateforyou.best.utils.isGenderSet
import com.dateforyou.best.utils.isRegistered
import com.dateforyou.best.utils.isSettingsSet
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MySplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(1000)

            finish()
            if(!isRegistered(this@MySplashActivity)) {
                startActivity(RegisterActivity.newIntent(this@MySplashActivity))
            }
            else if(!isSettingsSet(this@MySplashActivity)) {
                startActivity(UserDataActivity.newIntent(this@MySplashActivity))
            }
            else if(!isGenderSet(this@MySplashActivity)) {
                startActivity(GenderActivity.newIntent(this@MySplashActivity))
            }
            else {
                startActivity(ChooseActivity.newIntent(this@MySplashActivity))
            }
        }
    }
}