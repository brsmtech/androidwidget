package com.bstech.widapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.bstech.widapp.R
import com.bstech.widlib.util.Validation
import com.bstech.widlib.view.EditText2View

/**
 * Created by brayskiy on 2/22/18.
 */

class PasswordActivity : AppCompatActivity() {
    private lateinit var password: EditText2View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        password = findViewById(R.id.password_password)
        password.setValidation(Validation.Action.PASSWORD)
    }
}
