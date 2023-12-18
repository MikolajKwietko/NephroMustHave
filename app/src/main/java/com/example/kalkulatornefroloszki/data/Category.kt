package com.example.kalkulatornefroloszki.data

import java.util.ArrayList

object Category {
    val ITEMS: MutableList<Card> = ArrayList()

    data class Card(
        var name: String,
        var image: Int
    )
}