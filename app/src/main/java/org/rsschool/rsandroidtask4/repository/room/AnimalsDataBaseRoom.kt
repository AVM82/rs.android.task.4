package org.rsschool.rsandroidtask4.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.repository.AnimalsDataBase

@Database(
    entities = [Animal::class],
    version = AnimalsDataBase.DATABASE_VERSION,
    exportSchema = false
)
abstract class AnimalsDataBaseRoom : RoomDatabase(), AnimalsDataBase