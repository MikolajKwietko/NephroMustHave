package com.example.kalkulatornefroloszki

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.kalkulatornefroloszki.data.Card
import com.example.kalkulatornefroloszki.data.Card.Category
import com.example.kalkulatornefroloszki.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), CategoryListListener {

    private lateinit var binding: FragmentHomeBinding

    private var columnCount = 2

    private val categories = listOf(
        Category("eGFR", R.drawable.egfr),
        Category("Leki", R.drawable.leki),
        Category("ABPM", R.drawable.abpm),
        Category("Hiponatremia", R.drawable.nacl),
        Category("Nepresol", R.drawable.leki),
        Category("Frakcyjne wydalanie sodu", R.drawable.nacl),
        Category("Nefrologia", R.drawable.logo, arrayListOf(
                Category("eGFR", R.drawable.egfr),
                Category("Hiponatremia", R.drawable.nacl),
                Category("Frakcyjne wydalanie sodu", R.drawable.nacl)
            )
        )
    )

    private val categoryStack = ArrayList<Pair<String, List<Category>>>()

    private val categoryAdapter by lazy {
        MyCategoryRecyclerViewAdapter(Card.ITEMS, this@HomeFragment)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Card.ITEMS.clear()
        for (i in categories.indices) {
            Card.ITEMS.add(categories[i])
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
            adapter = categoryAdapter
        }

        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (categoryStack.size > 1) {
                    categoryStack.removeLast()
                    categoryAdapter.setCategory(categoryStack.last().second)
                    binding.calcHomeTitle.text = categoryStack.last().first
                } else if (categoryStack.size == 1) {
                    categoryStack.removeLast()
                    categoryAdapter.setCategory(categories)
                    binding.calcHomeTitle.text = getString(R.string.app_name)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

        return binding.root
    }

    override fun onItemClick(position: Int) {
        if (categoryStack.isNotEmpty() && categoryStack.last().second[position].subcategories.isNotEmpty()){
            updateSubcategory(categoryStack.last().second[position])
        } else if (categoryStack.isEmpty() && categories[position].subcategories.isNotEmpty()){
            updateSubcategory(categories[position])
        } else {
            when (position) {
                0 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToCalcFragment())
                /*1 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToMedicineFragment())*/
                1 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToCalculateFragment())
                2 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToCalcABPMFragment())
                3 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToHiponatremiaFragment())
                4 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToNepresolFragment())
                5 -> navigateToFragment(HomeFragmentDirections.actionHomeFragmentToFenaFragment())
            }
        }
    }

    private fun navigateToFragment(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun updateSubcategory(category: Category) {
        categoryAdapter.setCategory(category.subcategories)
        categoryStack.add(Pair(category.name, category.subcategories))
        binding.calcHomeTitle.text = category.name
    }
}