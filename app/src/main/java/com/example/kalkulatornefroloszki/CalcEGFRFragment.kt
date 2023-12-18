package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kalkulatornefroloszki.databinding.FragmentCalcEgfrBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


class CalcEGFRFragment : Fragment() {

    private lateinit var binding: FragmentCalcEgfrBinding
    private var eGFR = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalcEgfrBinding.inflate(inflater, container, false)

        setupUI(binding.calcEGFRContainer)

        eGFR = 0.0

        binding.resultEGFR.text = ""
        binding.resultEGFR.visibility = View.GONE
        binding.resultEGFRLabel.visibility = View.GONE
        binding.resultEGFRUnit.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.egfrButton.setOnClickListener { calculateEGFR() }
    }

    private fun calculateEGFR() {
        if (binding.egfrHeightInput.text.isEmpty()){
            Snackbar.make(binding.root, "Brak podanego wzrostu dziecka", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#770000"))
                .show()
        } else if (binding.egfrCreatinineInput.text.isEmpty()){
            Snackbar.make(binding.root, "Brak podanego stężenia kreatyniny", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#770000"))
                .show()
        } else {
            eGFR = (0.413 * binding.egfrHeightInput.text.toString().replace(",", ".").toDouble()) / binding.egfrCreatinineInput.text.toString().replace(",", ".").toDouble()
            eGFR = (eGFR * 100.0).roundToInt() / 100.0

            binding.resultEGFR.text = eGFR.toString().replace(".", ",")
            switchResultVisibilityOn()
        }
    }

    private fun switchResultVisibilityOn(){
        if(binding.resultEGFRLabel.visibility == View.GONE) {
            binding.resultEGFRLabel.visibility = View.VISIBLE
        }
        if(binding.resultEGFR.visibility == View.GONE) {
            binding.resultEGFR.visibility = View.VISIBLE
        }
        if(binding.resultEGFRUnit.visibility == View.GONE){
            binding.resultEGFRUnit.visibility = View.VISIBLE
        }
    }

    private fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }
}