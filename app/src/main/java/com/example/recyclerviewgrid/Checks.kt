package com.example.recyclerviewgrid

import com.example.recyclerviewgrid.data.Person
import com.example.recyclerviewgrid.databinding.ActivityItemOneBinding
import com.example.recyclerviewgrid.enums.ZodiacSigns

fun isEmptyField(
    firstName: String,
    lastName: String,
    sign: String
): Boolean {
    return firstName.isEmpty() ||
            lastName.isEmpty() ||
            sign.isEmpty()
}
