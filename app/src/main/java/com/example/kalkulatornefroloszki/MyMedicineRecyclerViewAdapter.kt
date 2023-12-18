package com.example.kalkulatornefroloszki

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.*
import com.example.kalkulatornefroloszki.databinding.FragmentMedicineChildBinding
import com.example.kalkulatornefroloszki.databinding.FragmentMedicineParentBinding

class MyMedicineRecyclerViewAdapter (
    var context: Context,
    private val values:  MutableList<ExpandableMedicineList>,
    private val eventListener: MedicineListListener
) : Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            ExpandableMedicineList.PARENT -> {MedicineParentViewHolder(
                FragmentMedicineParentBinding.inflate(LayoutInflater.from(parent.context), parent, false))}

            ExpandableMedicineList.CHILD ->
            {MedicineChildViewHolder(FragmentMedicineChildBinding.inflate(LayoutInflater.from(parent.context), parent, false))}

            else -> {MedicineParentViewHolder(
                FragmentMedicineParentBinding.inflate(LayoutInflater.from(parent.context), parent, false))}
        }
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = values[position]
        when(row.type){
            ExpandableMedicineList.PARENT -> {
                (holder as MedicineParentViewHolder).medicineParentName.text = row.medicineParent.name
                holder.container.setOnClickListener{
                    if (!row.isExpanded) {
                        holder.image.setImageResource(R.drawable.ic_collapse_arrow)
                        row.isExpanded = true
                        expandRow(position)
                    } else {
                        holder.image.setImageResource(R.drawable.ic_expand_arrow)
                        row.isExpanded = false
                        collapseRow(position)
                    }
                    eventListener.onParentClick(this, row.medicineParent, row)
                }
            }
            ExpandableMedicineList.CHILD -> {
                (holder as MedicineChildViewHolder).medicineChildName.text = row.medicineChild.name
                holder.container.setOnClickListener{
                    eventListener.onChildClick(row.medicineChild)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = values[position].type

    @SuppressLint("NotifyDataSetChanged")
    private fun expandRow(position: Int) {
        val row = values[position]
        var nextPosition = position
        when (row.type) {
            ExpandableMedicineList.PARENT -> {
                for (child in row.medicineParent.group){
                    values.add(++nextPosition, ExpandableMedicineList(ExpandableMedicineList.CHILD, child))
                }
                notifyDataSetChanged()
            }
            ExpandableMedicineList.CHILD -> {
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collapseRow(position: Int) {
        val row = values[position]
        val nextPosition = position + 1
        when (row.type) {
            ExpandableMedicineList.PARENT -> {
                outerLoop@while (true) {
                    if (nextPosition == values.size || values[nextPosition].type == ExpandableMedicineList.PARENT){
                        break@outerLoop
                    }
                    values.removeAt(nextPosition)
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class MedicineParentViewHolder(binding: FragmentMedicineParentBinding) : ViewHolder(binding.root) {
        val container = binding.medicineParentContainer
        val image = binding.expandMedicineListImage
        var medicineParentName = binding.medicineParentName
    }

    inner class MedicineChildViewHolder(binding: FragmentMedicineChildBinding) : ViewHolder(binding.root) {
        val container = binding.medicineChildContainer
        var medicineChildName = binding.medicineChildName
    }

}