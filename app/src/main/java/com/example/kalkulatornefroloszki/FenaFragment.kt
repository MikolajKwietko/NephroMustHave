package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kalkulatornefroloszki.Utils.InputField
import com.example.kalkulatornefroloszki.Utils.inputToDouble
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

    Utils.setupUI(this, binding.root)

    binding.fenaResult.visibility = View.GONE

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.fenaCalculateButton.setOnClickListener { calculateFena() }
  }

  @SuppressLint("SetTextI18n")
  private fun calculateFena() {
    val inputs = listOf(
      InputField(binding.fenaSerumCreatinineInput, "Brak podanego stężenia kreatyniny w osoczu"),
      InputField(binding.fenaSerumSodiumInput, "Brak podanego stężenia sodu w osoczu"),
      InputField(binding.fenaUrineCreatinineInput, "Brak podanego stężenia kreatyniny w moczu"),
      InputField(binding.fenaUrineSodiumInput, "Brak podanego stężenia sodu w moczu")
    )
    if(Utils.checkInputsIfEmpty(*inputs.toTypedArray())){
      val ucr = inputToDouble(binding.fenaUrineCreatinineInput)
      val una = inputToDouble(binding.fenaUrineSodiumInput)
      val scr = inputToDouble(binding.fenaSerumCreatinineInput)
      val sna = inputToDouble(binding.fenaSerumSodiumInput)
      val result = (100 * ((scr * una) / (sna * ucr)))
      resultText = if (result < 1) "Przyczyna przednerkowa"
      else if (result > 4) "Przyczyna zanerkowa"
      else "Przyczyna nerkowa"
      binding.fenaResult.text = "FENa: ${Utils.doubleToString(requireContext(), R.string.wynik_fena, result)}%\n $resultText"
      binding.fenaResult.visibility = View.VISIBLE
    }
  }
}