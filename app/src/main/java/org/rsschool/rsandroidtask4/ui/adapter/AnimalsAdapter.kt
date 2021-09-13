package org.rsschool.rsandroidtask4.ui.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.rsschool.rsandroidtask4.data.Animal

class AnimalsAdapter(private val listener: AnimalViewHolder.ItemListener) :
    ListAdapter<Animal, AnimalViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder =
        AnimalViewHolder.from(parent)

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) =
        holder.bind(getItem(position), listener)
}