package com.example.storyapp.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class EditTextEmail: TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                error = if (!isValidEmail(p0)) "The email is not valid" else null
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
    private fun isValidEmail(email: CharSequence?): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }
}