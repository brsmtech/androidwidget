package com.bstech.widapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.bstech.widapp.R
import com.bstech.widlib.util.Validation
import com.bstech.widlib.view.EditTextView

/**
 * Created by brayskiy on 2/22/18.
 */

class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditTextView
    private lateinit var loginPassword: EditTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.login_email)
        loginEmail.setValidation(Validation.Action.EMAIL)

        loginPassword = findViewById(R.id.login_password)
        loginPassword.setValidation(Validation.Action.PASSWORD)
    }
}
