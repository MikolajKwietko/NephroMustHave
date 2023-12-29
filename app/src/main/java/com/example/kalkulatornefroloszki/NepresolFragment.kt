package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.kalkulatornefroloszki.databinding.FragmentNepresolBinding
import kotlin.math.roundToInt


class NepresolFragment : Fragment() {

  private lateinit var binding: FragmentNepresolBinding
  private var weight = 0.0

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentNepresolBinding.inflate(inflater, container, false)

    setupUI(binding.nepresolContainer)

    binding.nepresolResult.visibility = View.GONE

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.nepresolCalculateButton.setOnClickListener{ calculateDose() }
  }

  private fun calculateDose() {
    weight = binding.nepresolWeightInput.text.toString().replace(",", ".").toDouble()
    weight = (weight * 100.0).roundToInt() / 100.0

    val dayDose = (weight * 60 * 24)/1000
    val result = 24/(dayDose/25)

    binding.nepresolResult.text = getString(R.string.wynik_nepresol, String.format("%.2f", result).replace(".", ","))
    binding.nepresolResult.visibility = View.VISIBLE
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