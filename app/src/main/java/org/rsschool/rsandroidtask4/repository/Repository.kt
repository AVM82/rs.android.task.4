package org.rsschool.rsandroidtask4.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.rsschool.rsandroidtask4.data.Animal
import javax.inject.Inject

class Repository @Inject constructor(private val db: AnimalsDataBase) {
    private val dao get() = db.animalsDAO

    fun getAll(order: String): Flow<List<Animal>>
    {
        Log.d("ORDER BY", order)
        return dao.getAll("1.0")
    }

    suspend fun save(animal: Animal) = dao.add(animal)

    suspend fun delete(animal: Animal) = dao.delete(animal)
}
