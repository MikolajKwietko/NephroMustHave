package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.kalkulatornefroloszki.databinding.FragmentCalcAbpmBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.round

class CalcABPMFragment : Fragment() {

    private lateinit var binding: FragmentCalcAbpmBinding

    private val boy24SBP     = arrayListOf(116.8, 117.8, 118.9, 120.0, 121.2, 122.5, 124.0, 125.7, 127.4, 129.3, 131.1, 132.6, 134.1, 135.5)
    private val boy24DBP     = arrayListOf(76.7, 76.8, 76.9, 76.9, 77.0, 77.1, 77.1, 77.2, 77.3, 77.5, 77.7, 77.8, 77.9, 78.0)
    private val boyDaySBP    = arrayListOf(125.2, 125.3, 125.5, 125.7, 126.0, 126.9, 128.3, 130.2, 132.7, 135.3, 137.6, 139.6, 141.6, 143.5)
    private val boyDayDBP    = arrayListOf(82.4, 82.2, 82.0, 81.8, 81.5, 81.4, 81.2, 81.2, 81.3, 81.7, 82.1, 82.6, 83.1, 83.6)
    private val boyNightSBP  = arrayListOf(106.3, 107.9, 109.7, 111.4, 113.0, 114.4, 115.7, 116.8, 118.1, 119.4, 120.9, 122.4, 123.9, 125.3)
    private val boyNightDBP  = arrayListOf(62.6, 63.4, 64.2, 64.8, 65.4, 65.8, 66.0, 66.0, 66.0, 66.0, 66.1, 66.1, 66.1, 66.2)
    private val girl24SBP    = arrayListOf(114.3, 115.6, 116.7, 117.7, 118.7, 119.9, 121.2, 122.5, 123.3, 124.1, 124.9, 125.8)
    private val girl24DBP    = arrayListOf(72.2, 72.8, 73.4, 74.1, 74.7, 75.3, 75.7, 76.0, 76.1, 76.2, 76.2, 76.2)
    private val girlDaySBP   = arrayListOf(120.4, 121.3, 122.1, 122.9, 123.8, 125.1, 126.5, 127.9, 129.1, 129.8, 130.5, 131.0)
    private val girlDayDBP   = arrayListOf(81.9, 81.8, 81.8, 81.8, 81.9, 82.0, 82.0, 82.0, 82.0, 81.9, 82.0, 82.0)
    private val girlNightSBP = arrayListOf(105.6, 106.9, 108.1, 109.3, 110.4, 111.6, 112.7, 113.6, 114.1, 114.4, 114.8, 115.3)
    private val girlNightDBP = arrayListOf(65.2, 65.5, 65.7, 65.8, 65.8, 65.7, 65.6, 65.5, 65.5, 65.5, 70.8, 65.5)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalcAbpmBinding.inflate(inflater, container, false)

        setupUI(binding.calcABPMContainer)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.abpmBoyButton.setOnClickListener {
            if (binding.abpmHeightInput.text.isNotEmpty()) calculateABPMBoy(binding.abpmHeightInput.text.toString().replace(",", ".").toDouble())
            binding.abpmBoyButton.setTextColor(Color.WHITE)
            binding.abpmGirlButton.setTextColor(Color.parseColor("#FF990000"))
        }
        binding.abpmGirlButton.setOnClickListener {
            if (binding.abpmHeightInput.text.isNotEmpty()) calculateABPMGirl(binding.abpmHeightInput.text.toString().replace(",", ".").toDouble())
            binding.abpmGirlButton.setTextColor(Color.WHITE)
            binding.abpmBoyButton.setTextColor(Color.parseColor("#FF990000"))
        }

        binding.abpmCalculateButton.setOnClickListener {
            if (binding.abpmHeightInput.text.isEmpty()){
                Snackbar.make(binding.root, "Brak podanego wzrostu dziecka", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#770000"))
                    .show()
                hideResultVisibility(true)
            } else {
                hideResultVisibility(false)
                if (binding.abpmBoyButton.isChecked) {
                    calculateABPMBoy(binding.abpmHeightInput.text.toString().replace(",", ".").toDouble())
                } else calculateABPMGirl(binding.abpmHeightInput.text.toString().replace(",", ".").toDouble())
            }
        }
    }

    private fun hideResultVisibility(hide: Boolean){
        if (hide){
            if (binding.abpmResultLabelContainer.visibility != View.GONE) binding.abpmResultLabelContainer.visibility = View.GONE
            if (binding.abpmResultContainer.visibility != View.GONE) binding.abpmResultContainer.visibility = View.GONE
            if (binding.abpm24Label.visibility != View.GONE) binding.abpm24Label.visibility = View.GONE
            if (binding.abpm24Result.visibility != View.GONE) binding.abpm24Result.visibility = View.GONE
        } else {
            if (binding.abpmResultLabelContainer.visibility != View.VISIBLE) binding.abpmResultLabelContainer.visibility = View.VISIBLE
            if (binding.abpmResultContainer.visibility != View.VISIBLE) binding.abpmResultContainer.visibility = View.VISIBLE
            if (binding.abpm24Label.visibility != View.VISIBLE) binding.abpm24Label.visibility = View.VISIBLE
            if (binding.abpm24Result.visibility != View.VISIBLE) binding.abpm24Result.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateABPMBoy(height: Double){
        if (binding.abpmWarnContainer.visibility != View.GONE) binding.abpmWarnContainer.visibility = View.GONE
        when(5*(round(height / 5))){
            120.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[0]).toInt()}/${round(boyDayDBP[0]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[0]).toInt()}/${round(boyNightDBP[0]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[0]).toInt()}/${round(boy24DBP[0]).toInt()}"
            }
            125.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[1]).toInt()}/${round(boyDayDBP[1]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[1]).toInt()}/${round(boyNightDBP[1]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[1]).toInt()}/${round(boy24DBP[1]).toInt()}"
            }
            130.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[2]).toInt()}/${round(boyDayDBP[2]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[2]).toInt()}/${round(boyNightDBP[2]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[2]).toInt()}/${round(boy24DBP[2]).toInt()}"
            }
            135.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[3]).toInt()}/${round(boyDayDBP[3]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[3]).toInt()}/${round(boyNightDBP[3]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[3]).toInt()}/${round(boy24DBP[3]).toInt()}"
            }
            140.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[4]).toInt()}/${round(boyDayDBP[4]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[4]).toInt()}/${round(boyNightDBP[4]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[4]).toInt()}/${round(boy24DBP[4]).toInt()}"
            }
            145.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[5]).toInt()}/${round(boyDayDBP[5]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[5]).toInt()}/${round(boyNightDBP[5]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[5]).toInt()}/${round(boy24DBP[5]).toInt()}"
            }
            150.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[6]).toInt()}/${round(boyDayDBP[6]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[6]).toInt()}/${round(boyNightDBP[6]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[6]).toInt()}/${round(boy24DBP[6]).toInt()}"
            }
            155.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[7]).toInt()}/${round(boyDayDBP[7]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[7]).toInt()}/${round(boyNightDBP[7]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[7]).toInt()}/${round(boy24DBP[7]).toInt()}"
            }
            160.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[8]).toInt()}/${round(boyDayDBP[8]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[8]).toInt()}/${round(boyNightDBP[8]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[8]).toInt()}/${round(boy24DBP[8]).toInt()}"
            }
            165.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[9]).toInt()}/${round(boyDayDBP[9]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[9]).toInt()}/${round(boyNightDBP[9]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[9]).toInt()}/${round(boy24DBP[9]).toInt()}"
            }
            170.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[10]).toInt()}/${round(boyDayDBP[10]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[10]).toInt()}/${round(boyNightDBP[10]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[10]).toInt()}/${round(boy24DBP[10]).toInt()}"
            }
            175.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[11]).toInt()}/${round(boyDayDBP[11]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[11]).toInt()}/${round(boyNightDBP[11]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[11]).toInt()}/${round(boy24DBP[11]).toInt()}"
            }
            180.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[12]).toInt()}/${round(boyDayDBP[12]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[12]).toInt()}/${round(boyNightDBP[12]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[12]).toInt()}/${round(boy24DBP[12]).toInt()}"
            }
            185.0 -> {
                binding.abpmDayResult.text = "${round(boyDaySBP[13]).toInt()}/${round(boyDayDBP[13]).toInt()}"
                binding.abpmNightResult.text = "${round(boyNightSBP[13]).toInt()}/${round(boyNightDBP[13]).toInt()}"
                binding.abpm24Result.text = "${round(boy24SBP[13]).toInt()}/${round(boy24DBP[13]).toInt()}"
            }
            else -> {
                if (binding.abpmWarnContainer.visibility != View.VISIBLE) binding.abpmWarnContainer.visibility = View.VISIBLE
                if (height > 187.5){
                    binding.abpmWarnText.text = "Mutant"
                    binding.abpmDayResult.text = "${round(boyDaySBP[13]).toInt()}/${round(boyDayDBP[13]).toInt()}"
                    binding.abpmNightResult.text = "${round(boyNightSBP[13]).toInt()}/${round(boyNightDBP[13]).toInt()}"
                    binding.abpm24Result.text = "${round(boy24SBP[13]).toInt()}/${round(boy24DBP[13]).toInt()}"
                } else {
                    binding.abpmWarnText.text = "Hobbit"
                    binding.abpmDayResult.text = "${round(boyDaySBP[0]).toInt()}/${round(boyDayDBP[0]).toInt()}"
                    binding.abpmNightResult.text = "${round(boyNightSBP[0]).toInt()}/${round(boyNightDBP[0]).toInt()}"
                    binding.abpm24Result.text = "${round(boy24SBP[0]).toInt()}/${round(boy24DBP[0]).toInt()}"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateABPMGirl(height: Double){
        if (binding.abpmWarnContainer.visibility != View.GONE) binding.abpmWarnContainer.visibility = View.GONE
        when(5*(round(height / 5))){
            120.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[0]).toInt()}/${round(girlDayDBP[0]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[0]).toInt()}/${round(girlNightDBP[0]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[0]).toInt()}/${round(girl24DBP[0]).toInt()}"
            }
            125.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[1]).toInt()}/${round(girlDayDBP[1]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[1]).toInt()}/${round(girlNightDBP[1]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[1]).toInt()}/${round(girl24DBP[1]).toInt()}"
            }
            130.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[2]).toInt()}/${round(girlDayDBP[2]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[2]).toInt()}/${round(girlNightDBP[2]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[2]).toInt()}/${round(girl24DBP[2]).toInt()}"
            }
            135.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[3]).toInt()}/${round(girlDayDBP[3]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[3]).toInt()}/${round(girlNightDBP[3]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[3]).toInt()}/${round(girl24DBP[3]).toInt()}"
            }
            140.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[4]).toInt()}/${round(girlDayDBP[4]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[4]).toInt()}/${round(girlNightDBP[4]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[4]).toInt()}/${round(girl24DBP[4]).toInt()}"
            }
            145.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[5]).toInt()}/${round(girlDayDBP[5]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[5]).toInt()}/${round(girlNightDBP[5]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[5]).toInt()}/${round(girl24DBP[5]).toInt()}"
            }
            150.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[6]).toInt()}/${round(girlDayDBP[6]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[6]).toInt()}/${round(girlNightDBP[6]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[6]).toInt()}/${round(girl24DBP[6]).toInt()}"
            }
            155.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[7]).toInt()}/${round(girlDayDBP[7]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[7]).toInt()}/${round(girlNightDBP[7]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[7]).toInt()}/${round(girl24DBP[7]).toInt()}"
            }
            160.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[8]).toInt()}/${round(girlDayDBP[8]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[8]).toInt()}/${round(girlNightDBP[8]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[8]).toInt()}/${round(girl24DBP[8]).toInt()}"
            }
            165.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[9]).toInt()}/${round(girlDayDBP[9]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[9]).toInt()}/${round(girlNightDBP[9]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[9]).toInt()}/${round(girl24DBP[9]).toInt()}"
            }
            170.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[10]).toInt()}/${round(girlDayDBP[10]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[10]).toInt()}/${round(girlNightDBP[10]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[10]).toInt()}/${round(girl24DBP[10]).toInt()}"
            }
            175.0 -> {
                binding.abpmDayResult.text = "${round(girlDaySBP[11]).toInt()}/${round(girlDayDBP[11]).toInt()}"
                binding.abpmNightResult.text = "${round(girlNightSBP[11]).toInt()}/${round(girlNightDBP[11]).toInt()}"
                binding.abpm24Result.text = "${round(girl24SBP[11]).toInt()}/${round(girl24DBP[11]).toInt()}"
            }
            else -> {
                if (binding.abpmWarnContainer.visibility != View.VISIBLE) binding.abpmWarnContainer.visibility = View.VISIBLE
                if (height > 177.5){
                    binding.abpmWarnText.text = "Mutant"
                    binding.abpmDayResult.text = "${round(girlDaySBP[11]).toInt()}/${round(girlDayDBP[11]).toInt()}"
                    binding.abpmNightResult.text = "${round(girlNightSBP[11]).toInt()}/${round(girlNightDBP[11]).toInt()}"
                    binding.abpm24Result.text = "${round(girl24SBP[11]).toInt()}/${round(girl24DBP[11]).toInt()}"
                } else {
                    binding.abpmWarnText.text = "Hobbit"
                    binding.abpmDayResult.text = "${round(girlDaySBP[0]).toInt()}/${round(girlDayDBP[0]).toInt()}"
                    binding.abpmNightResult.text = "${round(girlNightSBP[0]).toInt()}/${round(girlNightDBP[0]).toInt()}"
                    binding.abpm24Result.text = "${round(girl24SBP[0]).toInt()}/${round(girl24DBP[0]).toInt()}"
                }
            }
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