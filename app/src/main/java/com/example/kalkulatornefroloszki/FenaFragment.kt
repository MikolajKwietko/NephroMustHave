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
import com.example.kalkulatornefroloszki.databinding.FragmentFenaBinding

class FenaFragment : Fragment() {

  private lateinit var binding: FragmentFenaBinding

  private var resultText = ""

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentFenaBinding.inflate(inflater, container, false)

    setupUI(binding.fenaContainer)

    binding.fenaResult.visibility = View.GONE

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.fenaCalculateButton.setOnClickListener { calculateFena() }
  }

  private fun calculateFena() {
    val ucr = binding.fenaUrineCreatinineInput.text.toString().replace(",", ".").toDouble()
    val una = binding.fenaUrineSodiumInput.text.toString().replace(",", ".").toDouble()
    val scr = binding.fenaSerumCreatinineInput.text.toString().replace(",", ".").toDouble()
    val sna = binding.fenaSerumSodiumInput.text.toString().replace(",", ".").toDouble()
    val result = (100 * ((scr * una)/(sna * ucr)))
    resultText = if (result < 1) "pre-renal"
    else if (result > 4) "post-renal"
    else "intrinsic"
    binding.fenaResult.text = "${getString(R.string.wynik_fena, String.format(" % .2f", result).replace(".", ", "))}%, $resultText"
    binding.fenaResult.visibility = View.VISIBLE
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