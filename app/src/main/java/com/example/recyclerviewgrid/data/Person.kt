package com.example.recyclerviewgrid.data

import java.util.*

data class Person(
    var firstName: String,
    val withImage : Boolean,
    var lastName: String,
    var zodiac: String,
    val userID: Int = UUID.randomUUID().hashCode()
    )
