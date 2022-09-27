package com.example.smscodeinputapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smscodeinput.SmsCodeInputText
import com.example.smscodeinputapp.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var dataBinding: LoginActivityBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = LoginActivityBinding.inflate(layoutInflater, null, false)
        setContentView(dataBinding.root);

        dataBinding.phoneNumberInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val phone = dataBinding.phoneNumberInput.phoneText
                if (phone.length == 11) {
                    Toast.makeText(
                        applicationContext,
                        "input phone : ${phone}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        dataBinding.smsCodeInput.setOnInputListener(object : SmsCodeInputText.OnInputListener {
            override fun onSucess(codes: String?) {
                Toast.makeText(
                    applicationContext,
                    "验证码 : ${codes}",
                    Toast.LENGTH_SHORT
                ).show();
            }

            override fun onInput() {

            }

        })
    }

}