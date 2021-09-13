package org.rsschool.rsandroidtask4.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import org.rsschool.rsandroidtask4.data.Animal
import javax.inject.Inject

class Repository @Inject constructor(private val db: AnimalsDataBase) {
    private val dao get() = db.animalsDAO

    fun getAll(order: String): Flow<List<Animal>> {
        val query = SimpleSQLiteQuery("SELECT * FROM animals ORDER BY $order")
        return dao.getAll(query)
    }

    suspend fun save(animal: Animal) = dao.add(animal)

    suspend fun delete(animal: Animal) = dao.delete(animal)
}
