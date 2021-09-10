package org.rsschool.rsandroidtask4.repository

import kotlinx.coroutines.flow.Flow
import org.rsschool.rsandroidtask4.data.Animal

class Repository(private val db: AnimalsDataBase) {

    private val dao get() = db.animalsDAO

    fun getAll(): Flow<List<Animal>> = dao.getAll()

    suspend fun save(animal: Animal) = dao.add(animal)

    suspend fun delete(animal: Animal) = dao.delete(animal)
}