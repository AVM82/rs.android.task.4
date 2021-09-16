package org.rsschool.rsandroidtask4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalsDAO {

    @RawQuery(observedEntities = [Animal::class])
    fun getAll(query: SupportSQLiteQuery): Flow<List<Animal>>

    @Insert(onConflict = REPLACE)
    suspend fun add(animal: Animal): Long

    @Delete
    suspend fun delete(animal: Animal)

}
