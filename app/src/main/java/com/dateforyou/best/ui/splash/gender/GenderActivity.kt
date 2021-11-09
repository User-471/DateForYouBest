package com.dateforyou.best.ui.splash.gender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dateforyou.best.R
import com.dateforyou.best.ui.splash.choose.ChooseActivity
import com.dateforyou.best.utils.setGender
import kotlinx.android.synthetic.main.activity_gender.*

class GenderActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GenderActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        iv_female?.setOnClickListener {
            toChoose()
        }
        iv_male?.setOnClickListener {
            toChoose()
        }
    }

    private fun toChoose() {
        setGender(this, true)
        startActivity(ChooseActivity.newIntent(this))
    }
}