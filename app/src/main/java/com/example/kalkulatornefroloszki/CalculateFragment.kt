package com.example.kalkulatornefroloszki

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kalkulatornefroloszki.databinding.FragmentCalculateBinding

class CalculateFragment : Fragment() {

    private lateinit var binding: FragmentCalculateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalculateBinding.inflate(inflater, container, false)

        Utils.setupUI(this, binding.root)

        val inputRows = 2
        val resultRows = 2
        var inputNumber = 3
        val resultNumber = 3


        for (i in 0 until inputRows){
            val row = LinearLayout(requireContext())
            row.orientation = LinearLayout.HORIZONTAL

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.bottomMargin = 16
            params.weight = 1.0f

            row.layoutParams = params

            if (inputNumber >= 2) {
                for (j in 0 until 2) {
                    val inputLabel = generateTextView()
                    val input = generateEditText()
                    val inputWindow = generateLinearVerticalLayout()
                    inputWindow.addView(inputLabel)
                    inputWindow.addView(input)
                    row.addView(inputWindow)
                }
                inputNumber -= 2
                binding.calculateInputContainer.addView(row)
            } else if (inputNumber == 1) {
                val inputLabel = generateTextView()
                val input = generateEditText()
                val inputWindow = generateLinearVerticalLayout(2.0f)
                inputWindow.addView(inputLabel)
                inputWindow.addView(input)
                row.addView(generateEmptyView())
                row.addView(inputWindow)
                row.addView(generateEmptyView())
                binding.calculateInputContainer.addView(row)
            }
            binding.calculateTitle.text = "Title"
        }

        return binding.root
    }

    private fun generateEditText(hintText: String = "Hint") : EditText{
        val editText = EditText(requireContext())
        editText.gravity = Gravity.CENTER
        editText.textSize = 24f
        editText.setTypeface(null, Typeface.BOLD)
        editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        editText.keyListener = DigitsKeyListener.getInstance("0123456789.,")
        editText.hint = hintText
        editText.id = View.generateViewId()
        return editText
    }

    private fun generateTextView(text: String = "Input:") : TextView{
        val textView = TextView(requireContext())
        textView.gravity = Gravity.CENTER
        textView.textSize = 18f
        textView.setTextColor(Color.parseColor("#555555"))
        textView.setTypeface(null, Typeface.BOLD)
        textView.text = text
        textView.id = View.generateViewId()
        return textView
    }

    private fun generateLinearVerticalLayout(weight: Float = 1.0f) : LinearLayout{
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = weight
        params.gravity = Gravity.CENTER
        params.marginStart = 4
        params.marginEnd = 4
        linearLayout.layoutParams = params
        linearLayout.id = View.generateViewId()
        return linearLayout
    }

    private fun generateLinearHorizontalLayout() : LinearLayout{
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0)
        params.weight = 1.0f
        params.gravity = Gravity.CENTER
        params.topMargin = 4
        params.bottomMargin = 4
        linearLayout.layoutParams = params
        linearLayout.id = View.generateViewId()
        return linearLayout
    }

    private fun generateEmptyView() : View{
        val view = View(requireContext())
        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
        params.weight = 1.0f
        view.layoutParams = params
        return view
    }
}