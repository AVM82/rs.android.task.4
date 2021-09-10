package org.rsschool.rsandroidtask4.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import org.rsschool.rsandroidtask4.data.Animal

object DiffCallBack : DiffUtil.ItemCallback<Animal>() {
    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean = oldItem == newItem

}
