package org.rsschool.rsandroidtask4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        }

        views {
            name.text = item.name
            age.text = item.age.toString()
            breed.text = item.breed

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
