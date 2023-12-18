package com.example.kalkulatornefroloszki

import com.example.kalkulatornefroloszki.data.Medicines

interface MedicineListListener {
    fun onChildClick(medicine: Medicines.MedicineGroup.Medicine)
    fun onParentClick(adapter: MyMedicineRecyclerViewAdapter, medicineGroup: Medicines.MedicineGroup, row: ExpandableMedicineList)
}