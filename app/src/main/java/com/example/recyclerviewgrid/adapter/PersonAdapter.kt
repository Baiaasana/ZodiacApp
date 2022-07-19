package com.example.recyclerviewgrid.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewgrid.R
import com.example.recyclerviewgrid.data.Person
import com.example.recyclerviewgrid.data.listOfNewItems
import com.example.recyclerviewgrid.databinding.ActivityAddItemBinding
import com.example.recyclerviewgrid.databinding.ActivityItemOneBinding
import com.example.recyclerviewgrid.databinding.ActivityItemTwoBinding
import com.example.recyclerviewgrid.diffUtils.PersonsDiffUtils
import com.example.recyclerviewgrid.enums.ZodiacSigns
import com.example.recyclerviewgrid.isEmptyField

class PersonAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val listOfItems = mutableListOf<Person>()
    private lateinit var binding2: ActivityAddItemBinding


    companion object {
        const val WITH_IMAGE = 1
        const val WITHOUT_IMAGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == WITH_IMAGE)
            FirstViewHolder(ActivityItemOneBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
        else
            SecondViewHolder(
                ActivityItemTwoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstViewHolder -> {
                holder.bind()
            }
            is SecondViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfItems[position].withImage)
            WITH_IMAGE
        else
            WITHOUT_IMAGE
    }

    inner class FirstViewHolder(private val binding: ActivityItemOneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val model = listOfItems[adapterPosition]
            binding.tvName.text = model.firstName
            binding.tvLastname.text = model.lastName
            binding.tvSign.text = model.zodiac
            binding.menu.setOnClickListener {
                popupMenus(it)
            }
            setImage(model)
        }

        private fun setImage(item: Person) {
            when (item.zodiac) {
                ZodiacSigns.ARIES.sign -> binding.ivZodiac.setImageResource(R.drawable.aries)
                ZodiacSigns.TAURUS.sign -> binding.ivZodiac.setImageResource(R.drawable.taurus)
                ZodiacSigns.GEMINI.sign -> binding.ivZodiac.setImageResource(R.drawable.gemini)
                ZodiacSigns.CANCER.sign -> binding.ivZodiac.setImageResource(R.drawable.cancer)
                ZodiacSigns.LEO.sign -> binding.ivZodiac.setImageResource(R.drawable.leo)
                ZodiacSigns.VIRGO.sign -> binding.ivZodiac.setImageResource(R.drawable.virgo)
                ZodiacSigns.LIBRA.sign -> binding.ivZodiac.setImageResource(R.drawable.libra)
                ZodiacSigns.SCORPIO.sign -> binding.ivZodiac.setImageResource(R.drawable.scorpio)
                ZodiacSigns.SAGITTARIUS.sign -> binding.ivZodiac.setImageResource(R.drawable.sagnittarius)
                ZodiacSigns.CAPRICORN.sign -> binding.ivZodiac.setImageResource(R.drawable.capricorn)
                ZodiacSigns.AQUARIUS.sign -> binding.ivZodiac.setImageResource(R.drawable.aquarius)
                ZodiacSigns.PISCES.sign -> binding.ivZodiac.setImageResource(R.drawable.piscess)
                else -> binding.ivZodiac.setImageResource(R.drawable.incognito)
            }
        }

        private fun popupMenus(v:View) {

            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_edit->{
                        val model = listOfNewItems[adapterPosition]
                        binding2 = ActivityAddItemBinding.inflate(LayoutInflater.from(context))
                        val view = binding2.root
                        binding2.etFirstname.hint = model.firstName
                        binding2.etLastname.hint = model.lastName
                        binding2.etSign.hint = model.zodiac
                        binding2.checkBox.isChecked = model.withImage

                        AlertDialog.Builder(context)
                            .setView(view)
                            .setTitle(context.getString(R.string.edit_info))
                            .setIcon(R.drawable.ic_baseline_edit_24)
                            .setPositiveButton(context.getString(R.string.save)) { dialog, _ ->

                                val firstName = binding2.etFirstname.text.toString()
                                val lastName = binding2.etLastname.text.toString()
                                val sign = binding2.etSign.text.toString()
                                val withPhoto = binding2.checkBox.isChecked

                                when(false) {
                                     !isEmptyField(firstName, lastName, sign) -> Toast.makeText(context, context.getString(R.string.empty_fields), Toast.LENGTH_SHORT)
                                        .show()
                                    else -> {
                                        listOfNewItems.removeAt(adapterPosition)
                                        listOfNewItems.add(adapterPosition,Person(firstName, withPhoto, lastName, sign, userID = model.userID))
                                        Toast.makeText(context, context.getString(R.string.update_success), Toast.LENGTH_SHORT)
                                            .show()
                                        dialog.dismiss()
                                    }
                                }
                            }
                            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.menu_delete->{
                        val model = listOfNewItems[adapterPosition]
                        AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.remove_user))
                            .setIcon(R.drawable.ic_baseline_warning_amber_24)
                            .setMessage("გსურთ წაიშალოს ${model.firstName}-ის ინფორმაცია?")
                            .setPositiveButton(context.getString(R.string.yes)){
                                    dialog,_->
                                listOfNewItems.removeAt(adapterPosition)
                                setItems(listOfNewItems)
                                Toast.makeText(context,context.getString(R.string.remove_success),Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton(context.getString(R.string.no)){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }

    }

    inner class SecondViewHolder(private val binding: ActivityItemTwoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val model = listOfItems[adapterPosition]
            binding.tvName.text = model.firstName
            binding.tvLastname.text = model.lastName
            binding.tvSign.text = model.zodiac

            binding.menu.setOnClickListener {
                popupMenus(it)
            }
        }

        private fun popupMenus(v:View) {

            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_edit->{
                        val model = listOfNewItems[adapterPosition]
                        binding2 = ActivityAddItemBinding.inflate(LayoutInflater.from(context))
                        val view = binding2.root
                        binding2.etFirstname.hint = model.firstName
                        binding2.etLastname.hint = model.lastName
                        binding2.etSign.hint = model.zodiac
                        binding2.checkBox.isChecked = model.withImage

                        AlertDialog.Builder(context)
                            .setView(view)
                            .setTitle(context.getString(R.string.edit_info))
                            .setIcon(R.drawable.ic_baseline_edit_24)
                            .setPositiveButton(context.getString(R.string.save)) { dialog, _ ->
                                val firstName = binding2.etFirstname.text.toString()
                                val lastName = binding2.etLastname.text.toString()
                                val sign = binding2.etSign.text.toString()
                                val withPhoto = binding2.checkBox.isChecked

                                when(false) {
                                    !isEmptyField(firstName, lastName, sign) -> Toast.makeText(context, context.getString(R.string.empty_fields), Toast.LENGTH_SHORT)
                                        .show()
                                    else -> {
                                        listOfNewItems.removeAt(adapterPosition)
                                        listOfNewItems.add(adapterPosition,Person(firstName, withPhoto, lastName, sign, userID = model.userID))
                                        Toast.makeText(context, context.getString(R.string.update_success), Toast.LENGTH_SHORT)
                                            .show()
                                        dialog.dismiss()
                                    }
                                }
                            }
                            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.menu_delete->{
                        val model = listOfNewItems[adapterPosition]
                        AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.remove_user))
                            .setIcon(R.drawable.ic_baseline_delete_outline_24)
                            .setMessage("გსურთ წაიშალოს ${model.firstName}-ის ინფორმაცია?")
                            .setPositiveButton(context.getString(R.string.yes)){
                                    dialog,_->
                                listOfNewItems.removeAt(adapterPosition)
                                setItems(listOfNewItems)
                                Toast.makeText(context,context.getString(R.string.remove_success),Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton(context.getString(R.string.no)){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    fun setItems(newItems: MutableList<Person>) {
        val diffUtils = PersonsDiffUtils(newList = newItems, oldList = listOfItems)
        val result = DiffUtil.calculateDiff(diffUtils)
        listOfItems.clear()
        listOfItems.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }
}