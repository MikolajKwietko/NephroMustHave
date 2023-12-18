package com.example.kalkulatornefroloszki.data

import com.example.kalkulatornefroloszki.ExpandableMedicineList

object Medicines{
    val ITEMS: MutableList<ExpandableMedicineList> = ArrayList()

    data class MedicineGroup(
        var name: String = "Grupa",
        var group: ArrayList<Medicine> = arrayListOf()
    ){
        data class Medicine(
            var name: String,
            var dose: Double,
            var timesADay: Int = 1,
            var unit: String = "mg/kg",
            var label: String = "",
            var extraDosing: Boolean = false,
            var extraName: String = "",
            var extraDose: Double = 0.0,
            var extraTimesADay: Int = 1
        )
    }

}


