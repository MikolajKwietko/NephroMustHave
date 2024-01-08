package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kalkulatornefroloszki.data.BloodPressureData as BP
import com.example.kalkulatornefroloszki.databinding.FragmentCalcAbpmBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.round

class CalcABPMFragment : Fragment() {

    private lateinit var binding: FragmentCalcAbpmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalcAbpmBinding.inflate(inflater, container, false)

        Utils.setupUI(this, binding.root)

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
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[0]).toInt()}/${round(BP.boyDayDBP[0]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[0]).toInt()}/${round(BP.boyNightDBP[0]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[0]).toInt()}/${round(BP.boy24DBP[0]).toInt()}"
            }
            125.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[1]).toInt()}/${round(BP.boyDayDBP[1]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[1]).toInt()}/${round(BP.boyNightDBP[1]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[1]).toInt()}/${round(BP.boy24DBP[1]).toInt()}"
            }
            130.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[2]).toInt()}/${round(BP.boyDayDBP[2]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[2]).toInt()}/${round(BP.boyNightDBP[2]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[2]).toInt()}/${round(BP.boy24DBP[2]).toInt()}"
            }
            135.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[3]).toInt()}/${round(BP.boyDayDBP[3]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[3]).toInt()}/${round(BP.boyNightDBP[3]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[3]).toInt()}/${round(BP.boy24DBP[3]).toInt()}"
            }
            140.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[4]).toInt()}/${round(BP.boyDayDBP[4]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[4]).toInt()}/${round(BP.boyNightDBP[4]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[4]).toInt()}/${round(BP.boy24DBP[4]).toInt()}"
            }
            145.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[5]).toInt()}/${round(BP.boyDayDBP[5]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[5]).toInt()}/${round(BP.boyNightDBP[5]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[5]).toInt()}/${round(BP.boy24DBP[5]).toInt()}"
            }
            150.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[6]).toInt()}/${round(BP.boyDayDBP[6]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[6]).toInt()}/${round(BP.boyNightDBP[6]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[6]).toInt()}/${round(BP.boy24DBP[6]).toInt()}"
            }
            155.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[7]).toInt()}/${round(BP.boyDayDBP[7]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[7]).toInt()}/${round(BP.boyNightDBP[7]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[7]).toInt()}/${round(BP.boy24DBP[7]).toInt()}"
            }
            160.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[8]).toInt()}/${round(BP.boyDayDBP[8]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[8]).toInt()}/${round(BP.boyNightDBP[8]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[8]).toInt()}/${round(BP.boy24DBP[8]).toInt()}"
            }
            165.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[9]).toInt()}/${round(BP.boyDayDBP[9]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[9]).toInt()}/${round(BP.boyNightDBP[9]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[9]).toInt()}/${round(BP.boy24DBP[9]).toInt()}"
            }
            170.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[10]).toInt()}/${round(BP.boyDayDBP[10]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[10]).toInt()}/${round(BP.boyNightDBP[10]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[10]).toInt()}/${round(BP.boy24DBP[10]).toInt()}"
            }
            175.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[11]).toInt()}/${round(BP.boyDayDBP[11]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[11]).toInt()}/${round(BP.boyNightDBP[11]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[11]).toInt()}/${round(BP.boy24DBP[11]).toInt()}"
            }
            180.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[12]).toInt()}/${round(BP.boyDayDBP[12]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[12]).toInt()}/${round(BP.boyNightDBP[12]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[12]).toInt()}/${round(BP.boy24DBP[12]).toInt()}"
            }
            185.0 -> {
                binding.abpmDayResult.text = "${round(BP.boyDaySBP[13]).toInt()}/${round(BP.boyDayDBP[13]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.boyNightSBP[13]).toInt()}/${round(BP.boyNightDBP[13]).toInt()}"
                binding.abpm24Result.text = "${round(BP.boy24SBP[13]).toInt()}/${round(BP.boy24DBP[13]).toInt()}"
            }
            else -> {
                if (binding.abpmWarnContainer.visibility != View.VISIBLE) binding.abpmWarnContainer.visibility = View.VISIBLE
                if (height > 187.5){
                    binding.abpmWarnText.text = "Mutant"
                    binding.abpmDayResult.text = "${round(BP.boyDaySBP[13]).toInt()}/${round(BP.boyDayDBP[13]).toInt()}"
                    binding.abpmNightResult.text = "${round(BP.boyNightSBP[13]).toInt()}/${round(BP.boyNightDBP[13]).toInt()}"
                    binding.abpm24Result.text = "${round(BP.boy24SBP[13]).toInt()}/${round(BP.boy24DBP[13]).toInt()}"
                } else {
                    binding.abpmWarnText.text = "Hobbit"
                    binding.abpmDayResult.text = "${round(BP.boyDaySBP[0]).toInt()}/${round(BP.boyDayDBP[0]).toInt()}"
                    binding.abpmNightResult.text = "${round(BP.boyNightSBP[0]).toInt()}/${round(BP.boyNightDBP[0]).toInt()}"
                    binding.abpm24Result.text = "${round(BP.boy24SBP[0]).toInt()}/${round(BP.boy24DBP[0]).toInt()}"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateABPMGirl(height: Double){
        if (binding.abpmWarnContainer.visibility != View.GONE) binding.abpmWarnContainer.visibility = View.GONE
        when(5*(round(height / 5))){
            120.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[0]).toInt()}/${round(BP.girlDayDBP[0]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[0]).toInt()}/${round(BP.girlNightDBP[0]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[0]).toInt()}/${round(BP.girl24DBP[0]).toInt()}"
            }
            125.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[1]).toInt()}/${round(BP.girlDayDBP[1]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[1]).toInt()}/${round(BP.girlNightDBP[1]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[1]).toInt()}/${round(BP.girl24DBP[1]).toInt()}"
            }
            130.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[2]).toInt()}/${round(BP.girlDayDBP[2]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[2]).toInt()}/${round(BP.girlNightDBP[2]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[2]).toInt()}/${round(BP.girl24DBP[2]).toInt()}"
            }
            135.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[3]).toInt()}/${round(BP.girlDayDBP[3]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[3]).toInt()}/${round(BP.girlNightDBP[3]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[3]).toInt()}/${round(BP.girl24DBP[3]).toInt()}"
            }
            140.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[4]).toInt()}/${round(BP.girlDayDBP[4]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[4]).toInt()}/${round(BP.girlNightDBP[4]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[4]).toInt()}/${round(BP.girl24DBP[4]).toInt()}"
            }
            145.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[5]).toInt()}/${round(BP.girlDayDBP[5]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[5]).toInt()}/${round(BP.girlNightDBP[5]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[5]).toInt()}/${round(BP.girl24DBP[5]).toInt()}"
            }
            150.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[6]).toInt()}/${round(BP.girlDayDBP[6]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[6]).toInt()}/${round(BP.girlNightDBP[6]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[6]).toInt()}/${round(BP.girl24DBP[6]).toInt()}"
            }
            155.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[7]).toInt()}/${round(BP.girlDayDBP[7]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[7]).toInt()}/${round(BP.girlNightDBP[7]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[7]).toInt()}/${round(BP.girl24DBP[7]).toInt()}"
            }
            160.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[8]).toInt()}/${round(BP.girlDayDBP[8]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[8]).toInt()}/${round(BP.girlNightDBP[8]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[8]).toInt()}/${round(BP.girl24DBP[8]).toInt()}"
            }
            165.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[9]).toInt()}/${round(BP.girlDayDBP[9]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[9]).toInt()}/${round(BP.girlNightDBP[9]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[9]).toInt()}/${round(BP.girl24DBP[9]).toInt()}"
            }
            170.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[10]).toInt()}/${round(BP.girlDayDBP[10]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[10]).toInt()}/${round(BP.girlNightDBP[10]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[10]).toInt()}/${round(BP.girl24DBP[10]).toInt()}"
            }
            175.0 -> {
                binding.abpmDayResult.text = "${round(BP.girlDaySBP[11]).toInt()}/${round(BP.girlDayDBP[11]).toInt()}"
                binding.abpmNightResult.text = "${round(BP.girlNightSBP[11]).toInt()}/${round(BP.girlNightDBP[11]).toInt()}"
                binding.abpm24Result.text = "${round(BP.girl24SBP[11]).toInt()}/${round(BP.girl24DBP[11]).toInt()}"
            }
            else -> {
                if (binding.abpmWarnContainer.visibility != View.VISIBLE) binding.abpmWarnContainer.visibility = View.VISIBLE
                if (height > 177.5){
                    binding.abpmWarnText.text = "Mutant"
                    binding.abpmDayResult.text = "${round(BP.girlDaySBP[11]).toInt()}/${round(BP.girlDayDBP[11]).toInt()}"
                    binding.abpmNightResult.text = "${round(BP.girlNightSBP[11]).toInt()}/${round(BP.girlNightDBP[11]).toInt()}"
                    binding.abpm24Result.text = "${round(BP.girl24SBP[11]).toInt()}/${round(BP.girl24DBP[11]).toInt()}"
                } else {
                    binding.abpmWarnText.text = "Hobbit"
                    binding.abpmDayResult.text = "${round(BP.girlDaySBP[0]).toInt()}/${round(BP.girlDayDBP[0]).toInt()}"
                    binding.abpmNightResult.text = "${round(BP.girlNightSBP[0]).toInt()}/${round(BP.girlNightDBP[0]).toInt()}"
                    binding.abpm24Result.text = "${round(BP.girl24SBP[0]).toInt()}/${round(BP.girl24DBP[0]).toInt()}"
                }
            }
        }
    }
}