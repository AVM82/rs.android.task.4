package org.rsschool.rsandroidtask4.repository

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.data.AnimalsDAO
import org.rsschool.rsandroidtask4.repository.cursor.AnimalsDataBaseCursor
import javax.inject.Inject

class Repository @Inject constructor(
    private val room: AnimalsDataBase,
    private val cursor: AnimalsDataBaseCursor
) {

    private var strategy: StrategyDAO? = null

    fun setStrategy(strategy: StrategyDAO) {
        this.strategy = strategy
    }

    private fun getStrategyDao(): AnimalsDAO = when (strategy) {
        StrategyDAO.CURSOR -> cursor.animalsDAO
        else -> room.animalsDAO
    }

    fun getAll(order: String): Flow<List<Animal>> {
        val query = SimpleSQLiteQuery("SELECT * FROM animals ORDER BY $order")
        return getStrategyDao().getAll(query)
    }

    suspend fun save(animal: Animal): Long = getStrategyDao().add(animal)

    suspend fun delete(animal: Animal) = getStrategyDao().delete(animal)
}

