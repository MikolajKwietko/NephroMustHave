package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object Utils {
    data class InputField(val input: EditText, val errorMessage: String)

    fun checkInputsIfEmpty(vararg fields: InputField): Boolean {
        for (field in fields) {
            if (field.input.text.isEmpty()) {
                Snackbar.make(field.input.rootView, field.errorMessage, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#770000"))
                    .show()
                return false
            }
        }
        return true
    }

    fun inputToDouble(field: EditText): Double {
        return field.text.toString().replace(",", ".").toDouble()
    }

    fun doubleToString(context: Context, resId: Int, number: Double): String {
        return context.getString(resId, String.format("%.2f", number).replace(".", ","))
    }

    fun doubleToString(number: Double): String {
        return number.toString().replace(".", ",")
    }


    //Hide keyboard functionality
    private fun hideKeyboard(fragment: Fragment) {
        fragment.view?.let { fragment.requireActivity().hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(fragment: Fragment, view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideKeyboard(fragment)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(fragment, innerView)
            }
        }
    }
}