package com.example.kalkulatornefroloszki

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kalkulatornefroloszki.data.Category.Card
import com.example.kalkulatornefroloszki.databinding.FragmentCategoryBinding

class MyCategoryRecyclerViewAdapter(
    private val values: List<Card>,
    private val eventListener: CategoryListListener
) : RecyclerView.Adapter<MyCategoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.image.setImageResource(item.image)
        holder.description.text = item.name
        holder.container.setOnClickListener {
            eventListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.categoryImage
        val description: TextView = binding.categoryDescription
        val container: LinearLayout = binding.categoryContainer
    }

}