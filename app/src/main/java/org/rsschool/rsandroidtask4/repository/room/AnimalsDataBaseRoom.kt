package org.rsschool.rsandroidtask4.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.repository.AnimalsDataBase

@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class AnimalsDataBaseRoom : RoomDatabase(), AnimalsDataBase {

//    companion object {
//        @Volatile
//        private var INSTANCE: AnimalsDataBaseRoom? = null

//        fun getInstance(context: Context): AnimalsDataBaseRoom {
//
//            var instance = INSTANCE
//
//            if (instance == null) {
//                instance = databaseBuilder(
//                    context.applicationContext,
//                    AnimalsDataBaseRoom::class.java,
//                    "animals_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//            }
//            return instance
//        }
//    }
}