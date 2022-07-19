package com.example.recyclerviewgrid.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerviewgrid.data.Person

class PersonsDiffUtils(val newList: List<Person>, val oldList: List<Person>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].userID == newList[newItemPosition].userID
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }
}
