package org.rsschool.rsandroidtask4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rsschool.rsandroidtask4.R
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.databinding.AnimalListItemBinding

class AnimalViewHolder(private val binding: AnimalListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    interface ItemListener {
        fun onClickListener(item: Animal)
        fun onLongClickListener(item: Animal): Boolean
    }

    fun bind(item: Animal, listener: ItemListener) {
        itemView.run {
            setOnClickListener { listener.onClickListener(item) }
            setOnLongClickListener { listener.onLongClickListener(item) }
            views {
                name.text = context.getString(R.string.name_animal, item.name)
                age.text = context.getString(R.string.age_animal, item.age.toString())
                breed.text = context.getString(R.string.breed_animal, item.breed)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup) = AnimalListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::AnimalViewHolder)
    }

    private fun <T> views(block: AnimalListItemBinding.() -> T): T? = binding.block()
}
