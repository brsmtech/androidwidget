package com.bstech.widapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bstech.widapp.R
import com.bstech.widlib.view.NiceTextView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextViewLoginDemo : NiceTextView
    private lateinit var editTextViewPasswordDemo : NiceTextView
    private lateinit var niceTextViewDemo : NiceTextView
    private lateinit var fontTextViewDemo : NiceTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextViewLoginDemo = findViewById(R.id.edit_text_view_login_demo)
        editTextViewLoginDemo.setOnClickListener({
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        })

        editTextViewPasswordDemo = findViewById(R.id.edit_text_view_password_demo)
        editTextViewPasswordDemo.setOnClickListener({
            val intent = Intent(this@MainActivity, PasswordActivity::class.java)
            startActivity(intent)
        })

        niceTextViewDemo = findViewById(R.id.nice_text_view_demo)
        niceTextViewDemo.setOnClickListener({
            val intent = Intent(this@MainActivity, NiceTextViewActivity::class.java)
            startActivity(intent)
        })

        fontTextViewDemo = findViewById(R.id.font_text_view_demo)
        fontTextViewDemo.setOnClickListener({
            val intent = Intent(this@MainActivity, FontTextViewActivity::class.java)
            startActivity(intent)
        })

    }
}
