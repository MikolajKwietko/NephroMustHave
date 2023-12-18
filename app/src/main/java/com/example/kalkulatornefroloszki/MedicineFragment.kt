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
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kalkulatornefroloszki.data.Medicines
import com.example.kalkulatornefroloszki.data.Medicines.MedicineGroup.Medicine
import com.example.kalkulatornefroloszki.data.Medicines.MedicineGroup
import com.example.kalkulatornefroloszki.databinding.FragmentMedicineBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class MedicineFragment : Fragment(), MedicineListListener {

    private lateinit var binding: FragmentMedicineBinding

    private val medicineExpList =
        arrayListOf(
            ExpandableMedicineList(
                1,
                MedicineGroup(
                    "Przeciwb\uD83D\uDC40lowe",
                    arrayListOf(
                        Medicine("Paracetamol", 60.0, 4),
                        Medicine("Ibuprofen", 30.0, 3),
                        Medicine("Metamizol", 40.0, 4),
                    )
                ),
            ),
            ExpandableMedicineList(
                1,
                MedicineGroup(
                    "Antybiotyki",
                    arrayListOf(
                        Medicine("Cefuroksym", 100.0, 3, label="Max dawka", extraDosing = true, extraName = "Po CUM", extraDose = 60.0),
                        Medicine("Ceftriakson", 50.0, label="Max noworodki", extraDosing = true, extraName = "Max <50kg", extraDose = 80.0),
                    )
                )
            ),
            ExpandableMedicineList(
                1,
                MedicineGroup(
                    "Płuca",
                    arrayListOf(
                        Medicine("Salbutamol", 0.4, 4),
                        Medicine("Budezonid", 0.5, 2)
                    )
                )
            ),
            ExpandableMedicineList(
                1,
                MedicineGroup(
                    "Nerki",
                    arrayListOf(
                        Medicine("Dafurag", 0.5, 2, "ml/")
                    )
                )
            ),
            /*ExpandableMedicineList(
                1,
                MedicineGroup(
                    "Pusta grupa",
                    arrayListOf(
                        Medicine("Pusty lek", 0.0, 1, "μg/kg", extraDosing = true, extraName = "Dodatkowa", extraDose = 0.0)
                    )
                )
            ),*/
        )

    private lateinit var currentMedicine: Medicine
    private var memLength: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fillList(medicineExpList)

        currentMedicine = Medicine("", 0.0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMedicineBinding.inflate(inflater, container, false)

        setupUI(binding.medicineContainer)

        hideResultViews()
        setupSearchView()

        binding.medicineCalculateButton.setOnClickListener { calculateDose(currentMedicine) }

        // Set the adapter
        with(binding.medicineList) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyMedicineRecyclerViewAdapter(context, Medicines.ITEMS, this@MedicineFragment)
        }

        return binding.root
    }

    private fun setupSearchView() {
        binding.medicineListSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filterMedicineList(query!!)
                return true
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterMedicineList(query: String){
        if (query.replace("\\s+".toRegex(), " ").trim().length < memLength){
            fillList(medicineExpList)
        }
        memLength = query.replace("\\s+".toRegex(), " ").trim().length
        val filteredList = arrayListOf<ExpandableMedicineList>()
        for (listItem in Medicines.ITEMS) {
            when (listItem.type) {
                ExpandableMedicineList.PARENT -> {
                    if (listItem.medicineParent.group.any { medicine ->
                            medicine.name.lowercase().contains(query.replace("\\s+".toRegex(), " ").trim().lowercase())
                        }) {
                        val filteredChildGroup = arrayListOf<Medicine>()
                        for (child in listItem.medicineParent.group) {
                            if (child.name.lowercase().contains(query.replace("\\s+".toRegex(), " ").trim().lowercase())) {
                                filteredChildGroup.add(child)
                            }
                        }
                        filteredList.add(
                            ExpandableMedicineList(
                                1,
                                MedicineGroup(
                                    listItem.medicineParent.name,
                                    filteredChildGroup
                                ),
                                isExpanded = listItem.isExpanded
                            )
                        )
                        if (listItem.isExpanded){
                            for (child in filteredChildGroup) {
                                filteredList.add(ExpandableMedicineList(ExpandableMedicineList.CHILD, child))
                            }
                        }
                    }
                }
                ExpandableMedicineList.CHILD -> {

                }
            }
        }
        if (filteredList != Medicines.ITEMS) {
            fillList(filteredList)
        }
        binding.medicineList.adapter?.notifyDataSetChanged()
    }

    private fun fillList(list: ArrayList<ExpandableMedicineList>){
        Medicines.ITEMS.clear()
        for (i in list.indices) {
            Medicines.ITEMS.add(list[i])
        }
    }

    private fun hideResultViews(nameVisibility: Boolean = false) {
        if (!nameVisibility) binding.medicineNameLabel.visibility = View.GONE
        binding.medicineResultLabelContainer.visibility = View.GONE
        binding.medicineResultValueContainer.visibility = View.GONE
        binding.medicineResultDoseContainer.visibility = View.GONE
    }

    private fun calculateDose(medicine: Medicine){
        if (medicine.name == "") {
            hideResultViews()
            Snackbar.make(binding.root, getString(R.string.brak_leku), Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.parseColor("#770000"))
                .show()
        } else {
            if (!medicine.extraDosing){
                binding.medicineResultExtraLabel.visibility = View.GONE
                binding.medicineResultExtraValue.visibility = View.GONE
                binding.medicineResultExtraDose.visibility = View.GONE
            } else binding.medicineResultExtraLabel.text = getString(R.string.nazwa_dwukropek, medicine.extraName)

            if (binding.medicineWeightInput.text.isEmpty()){
                hideResultViews(true)
                Snackbar.make(binding.root, getString(R.string.brak_wagi), Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#770000"))
                    .show()
            } else {
                if (medicine.label.isNotEmpty()){
                    binding.medicineResultLabel.text = getString(R.string.nazwa_dwukropek, medicine.label)
                } else binding.medicineResultLabel.text = getString(R.string.dawka_leku)
                val temp = (binding.medicineWeightInput.text.toString().replace(",",".").toDouble() * medicine.dose)/medicine.timesADay
                val result = DecimalFormat("0.##").format(temp)
                binding.medicineResultValue.text =
                    getString(R.string.wynik_leki, result, medicine.unit).replace(".",",").substringBefore("/", "mg")

                if (medicine.extraDosing){
                    val extraTemp = (binding.medicineWeightInput.text.toString().replace(",",".").toDouble() * medicine.extraDose)/medicine.extraTimesADay
                    val extraResult = DecimalFormat("0.##").format(extraTemp)
                    binding.medicineResultExtraValue.text =
                        getString(R.string.wynik_leki, extraResult, medicine.unit).replace(".",",").substringBefore("/", "mg")
                    binding.medicineResultExtraLabel.visibility = View.VISIBLE
                    binding.medicineResultExtraValue.visibility = View.VISIBLE
                }

                binding.medicineResultLabelContainer.visibility = View.VISIBLE
                binding.medicineResultValueContainer.visibility = View.VISIBLE

                if (medicine.timesADay > 1) {
                    binding.medicineResultDose.text = getString(R.string.dawka_etykieta, medicine.timesADay)
                    if (medicine.extraDosing) {
                        if (medicine.extraTimesADay > 1) {
                            binding.medicineResultExtraDose.text = getString(R.string.dawka_etykieta, medicine.extraTimesADay)
                            binding.medicineResultExtraDose.visibility = View.VISIBLE
                        } else binding.medicineResultExtraDose.visibility = View.INVISIBLE
                    } else binding.medicineResultExtraDose.visibility = View.GONE
                    binding.medicineResultDoseContainer.visibility = View.VISIBLE
                    binding.medicineResultDose.visibility = View.VISIBLE
                } else binding.medicineResultDoseContainer.visibility = View.GONE

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
        } else {
            view.setOnFocusChangeListener { currentView, _ ->
                if (!currentView.hasFocus()){
                    hideKeyboard()
                }
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

    override fun onChildClick(medicine: Medicine) {
        binding.medicineNameLabel.text = medicine.name
        if (binding.medicineNameLabel.visibility == View.GONE) binding.medicineNameLabel.visibility = View.VISIBLE
        currentMedicine = medicine

        if (binding.medicineWeightInput.text.isEmpty()) {
            binding.medicineResultLabelContainer.visibility = View.GONE
            binding.medicineResultValueContainer.visibility = View.GONE
            binding.medicineResultDoseContainer.visibility = View.GONE
        } else {
            calculateDose(medicine)
        }
    }

    override fun onParentClick(adapter: MyMedicineRecyclerViewAdapter, medicineGroup: MedicineGroup, row: ExpandableMedicineList) {
        medicineExpList[medicineExpList.indexOfFirst { it.medicineParent.name == medicineGroup.name }].isExpanded = row.isExpanded
    }
}