package com.example.kalkulatornefroloszki

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kalkulatornefroloszki.data.Category
import com.example.kalkulatornefroloszki.data.Category.Card
import com.example.kalkulatornefroloszki.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), CategoryListListener {

    private lateinit var binding: FragmentHomeBinding

    private val calcList = arrayListOf(
        Card("eGFR", R.drawable.egfr),
        Card("Leki", R.drawable.leki),
        Card("ABPM", R.drawable.abpm),
        Card("Hiponatremia", R.drawable.nacl),
        Card("Nepresol", R.drawable.leki),
        Card("FENa", R.drawable.nacl)
    )

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Category.ITEMS.clear()
        for (i in calcList.indices) {
            Category.ITEMS.add(calcList[i])
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set the adapter
            with(binding.categoryList) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyCategoryRecyclerViewAdapter(Category.ITEMS, this@HomeFragment)
            }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        when(position) {
            0 -> {
                val actionHomeFragmentToCalcFragment =
                    HomeFragmentDirections.actionHomeFragmentToCalcFragment()
                findNavController().navigate(actionHomeFragmentToCalcFragment)
            }
            1 -> {
                val actionHomeFragmentToMedicineFragment =
                    HomeFragmentDirections.actionHomeFragmentToMedicineFragment()
                findNavController().navigate(actionHomeFragmentToMedicineFragment)
            }
            2 -> {
                val actionHomeFragmentToCalcABPMFragment =
                    HomeFragmentDirections.actionHomeFragmentToCalcABPMFragment()
                findNavController().navigate(actionHomeFragmentToCalcABPMFragment)
            }
            3 -> {
                val actionHomeFragmentToHiponatremiaFragment =
                    HomeFragmentDirections.actionHomeFragmentToHiponatremiaFragment()
                findNavController().navigate(actionHomeFragmentToHiponatremiaFragment)
            }
            4 -> {
                val actionHomeFragmentToNepresolFragment =
                    HomeFragmentDirections.actionHomeFragmentToNepresolFragment()
                findNavController().navigate(actionHomeFragmentToNepresolFragment)
            }
            5 -> {
                val actionHomeFragmentToFenaFragment =
                    HomeFragmentDirections.actionHomeFragmentToFenaFragment()
                findNavController().navigate(actionHomeFragmentToFenaFragment)
            }
        }
    }
}