package com.example.kalkulatornefroloszki

import com.example.kalkulatornefroloszki.data.Medicines

class ExpandableMedicineList {
    companion object{
        const val PARENT = 1
        const val CHILD = 2

    }
    lateinit var medicineParent: Medicines.MedicineGroup
    var type : Int
    lateinit var medicineChild : Medicines.MedicineGroup.Medicine
    var isExpanded : Boolean
    private var isCloseShown : Boolean

    constructor( type : Int, medicineParent: Medicines.MedicineGroup, isExpanded : Boolean = false, isCloseShown : Boolean = false ){
        this.type = type
        this.medicineParent = medicineParent
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown

    }


    constructor(type : Int, medicineChild : Medicines.MedicineGroup.Medicine, isExpanded : Boolean = false, isCloseShown : Boolean = false){
        this.type = type
        this.medicineChild = medicineChild
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown


    }
}