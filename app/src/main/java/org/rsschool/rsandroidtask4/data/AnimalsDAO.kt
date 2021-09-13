package org.rsschool.rsandroidtask4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalsDAO {

    @Query("SELECT * FROM animals WHERE age like :param")
    fun getAll(param: String): Flow<List<Animal>>

    @Insert(onConflict = REPLACE)
    suspend fun add(animal: Animal)

    @Delete
    suspend fun delete(animal: Animal)

}