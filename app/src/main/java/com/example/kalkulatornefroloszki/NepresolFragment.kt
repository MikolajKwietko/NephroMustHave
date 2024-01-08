package com.example.kalkulatornefroloszki

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    Utils.setupUI(this, binding.root)

    binding.nepresolResult.visibility = View.GONE

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.nepresolCalculateButton.setOnClickListener{ calculateDose() }
  }

  private fun calculateDose() {
    val inputs = listOf(
      Utils.InputField(binding.nepresolWeightInput, "Brak podanej wagi dziecka")
    )
    if (Utils.checkInputsIfEmpty(*inputs.toTypedArray())) {
      weight = Utils.inputToDouble(binding.nepresolWeightInput)
      weight = (weight * 100.0).roundToInt() / 100.0

      val dayDose = (weight * 60 * 24) / 1000
      val result = 24 / (dayDose / 25)

      binding.nepresolResult.text = Utils.doubleToString(requireContext(), R.string.wynik_nepresol, result)
      binding.nepresolResult.visibility = View.VISIBLE
    }
  }
}