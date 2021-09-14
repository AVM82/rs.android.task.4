package org.rsschool.rsandroidtask4.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.rsschool.rsandroidtask4.repository.AnimalsDataBase

@Entity(tableName = AnimalsDataBase.TABLE_NAME)
data class Animal(
    @ColumnInfo(name = AnimalsDataBase.COLUMN_TABLE_ANIMALS_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = AnimalsDataBase.COLUMN_TABLE_ANIMALS_NAME)
    val name: String,
    @ColumnInfo(name = AnimalsDataBase.COLUMN_TABLE_ANIMALS_AGE)
    val age: Double,
    @ColumnInfo(name = AnimalsDataBase.COLUMN_TABLE_ANIMALS_BREED)
    val breed: String
)
