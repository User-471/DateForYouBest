package com.dateforyou.best.ui.splash.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dateforyou.best.R
import com.dateforyou.best.ui.splash.data.UserDataActivity
import com.dateforyou.best.utils.hideKeyboard
import com.dateforyou.best.utils.isEmailValid
import com.dateforyou.best.utils.setRegistered
import com.dateforyou.best.utils.showErrorSnackbar
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        iv_register?.setOnClickListener {
            onRegisterClicked()
        }
    }

    private fun onRegisterClicked() {
        hideKeyboard(this, if (et_email.hasFocus()) et_email else et_password, cl_login)
        if (!et_email?.text.toString().isEmailValid()) {
            showErrorSnackbar(
                this,
                cl_login,
                getString(R.string.error_email_is_not_valid)
            )
            return
        }

        if (et_password?.text.toString().isEmpty()) {
            showErrorSnackbar(this, cl_login, getString(R.string.error_password_empty))
            return
        }

        registerUser()
    }

    private fun registerUser() {
        setRegistered(this, true)
        startActivity(UserDataActivity.newIntent(this))
    }
}