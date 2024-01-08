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
import com.example.kalkulatornefroloszki.databinding.FragmentHiponatremiaBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import kotlin.math.round
import kotlin.math.roundToInt

class HiponatremiaFragment : Fragment() {

    private lateinit var binding: FragmentHiponatremiaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHiponatremiaBinding.inflate(inflater, container, false)

        Utils.setupUI(this, binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.hiponCalculateButton.setOnClickListener{ calculate() }
    }

    private fun calculate() {
        val inputs = listOf(
            Utils.InputField(binding.hiponWeightInput, "Brak podanej masy ciała"),
            Utils.InputField(binding.hiponNatremiaInput, "Brak podanej natremii")
        )
        if (Utils.checkInputsIfEmpty(*inputs.toTypedArray())) {
            val deficiency = (140 - Utils.inputToDouble(binding.hiponNatremiaInput)) * Utils.inputToDouble(binding.hiponWeightInput) * 0.6
            var result = DecimalFormat("0.#").format(deficiency)
            binding.hiponDeficiencyResult.text = getString(R.string.wynik_leki, result, "mmol").replace(".",",")

            val amount24 = deficiency/2
            result = DecimalFormat("0.#").format(amount24)
            binding.hipon24AmountResult.text = getString(R.string.wynik_leki, result, "mmol").replace(".",",")

            val amount10NaCl24 = amount24 * 10/17
            result = DecimalFormat("0.#").format(amount10NaCl24)
            binding.hipon24Amount10NaClResult.text = getString(R.string.wynik_leki, result, "ml").replace(".",",")

            val addition = amount10NaCl24 / 3 * 7
            result = DecimalFormat("0.#").format(addition)
            binding.hiponAdditionResult.text = getString(R.string.wynik_leki, result, "ml").replace(".",",")

            val total = amount10NaCl24 + addition
            result = DecimalFormat("0.#").format(total)
            binding.hiponTotalResult.text = getString(R.string.wynik_leki, result, "ml").replace(".",",")

            val totalPerHour = total/24
            result = DecimalFormat("0.#").format(totalPerHour)
            binding.hiponTotalPerHourResult.text = getString(R.string.wynik_leki, result, "ml/h").replace(".",",")

            binding.hiponResultsContainer.visibility = View.VISIBLE
        }
    }
}