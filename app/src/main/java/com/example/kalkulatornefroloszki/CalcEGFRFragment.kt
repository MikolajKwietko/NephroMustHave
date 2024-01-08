package com.example.kalkulatornefroloszki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kalkulatornefroloszki.Utils.InputField
import com.example.kalkulatornefroloszki.databinding.FragmentCalcEgfrBinding
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

        Utils.setupUI(this, binding.root)

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
        val inputs = listOf(
            InputField(binding.egfrHeightInput, "Brak podanego wzrostu dziecka"),
            InputField(binding.egfrCreatinineInput, "Brak podanego stężenia kreatyniny")
        )
        if (Utils.checkInputsIfEmpty(*inputs.toTypedArray())) {
            eGFR = (0.413 * binding.egfrHeightInput.text.toString().replace(",", ".").toDouble()) / binding.egfrCreatinineInput.text.toString().replace(",", ".").toDouble()
            eGFR = (eGFR * 100.0).roundToInt() / 100.0

            binding.resultEGFR.text = Utils.doubleToString(eGFR)
            switchResultVisibilityOn()
        }
    }

    private fun switchResultVisibilityOn(){
        with(binding) {
            resultEGFRLabel.visibility = resultEGFRLabel.visibility.takeIf { it == View.GONE } ?: View.VISIBLE
            resultEGFR.visibility = resultEGFR.visibility.takeIf { it == View.GONE } ?: View.VISIBLE
            resultEGFRUnit.visibility = resultEGFRUnit.visibility.takeIf { it == View.GONE } ?: View.VISIBLE
        }

    }
}