package com.example.kalkulatornefroloszki.data

import java.util.ArrayList

object Card {
    val ITEMS: MutableList<Category> = ArrayList()

    data class Category(
        var name: String,
        var image: Int,
        var subcategories: List<Category> = listOf()
    )
}