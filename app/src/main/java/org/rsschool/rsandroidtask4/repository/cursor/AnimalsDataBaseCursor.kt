package org.rsschool.rsandroidtask4.repository.cursor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.data.AnimalsDAO
import org.rsschool.rsandroidtask4.repository.AnimalsDataBase

class AnimalsDataBaseCursor(context: Context) : AnimalsDataBase, AnimalsDAO, SQLiteOpenHelper(
    context,
    AnimalsDataBase.DATABASE_NAME,
    null,
    AnimalsDataBase.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(AnimalsDataBase.CREATE_TABLE_SQL)
        } catch (exception: SQLiteException) {
            Log.e(LOG_TAG, "Exception while trying to create database. $exception")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(LOG_TAG, "onUpgrade called")
    }

    companion object {
        private const val LOG_TAG = "AnimalsDataBaseCursor"
    }

    override val animalsDAO: AnimalsDAO
        get() = this

    override fun getAll(query: SupportSQLiteQuery): Flow<List<Animal>> {
        val cursor = readableDatabase.rawQuery(query.sql, null)
        val animalList = mutableListOf<Animal>()
        try {
            if (cursor.moveToFirst()) {
                do {
                    val animal = Animal(
                        id = cursor.getInt(cursor.getColumnIndex(AnimalsDataBase.COLUMN_TABLE_ANIMALS_ID)),
                        name = cursor.getString(cursor.getColumnIndex(AnimalsDataBase.COLUMN_TABLE_ANIMALS_NAME)),
                        age = cursor.getDouble(cursor.getColumnIndex(AnimalsDataBase.COLUMN_TABLE_ANIMALS_AGE)),
                        breed = cursor.getString(cursor.getColumnIndex(AnimalsDataBase.COLUMN_TABLE_ANIMALS_BREED))
                    )
                    animalList.add(animal)
                } while (cursor.moveToNext())
            }
        } catch (exception: SQLiteException) {
            Log.e(LOG_TAG, "Exception while trying select from database. $exception")
        } finally {
            cursor.close()
        }
        return flowOf(animalList).flowOn(Dispatchers.IO)
    }

    override suspend fun add(animal: Animal) {
        when (animal.id) {
            0 -> insertAnimal(animal)
            else -> updateAnimal()
        }
    }

    private fun updateAnimal() {
        TODO("Not yet implemented")
    }

    private fun insertAnimal(animal: Animal) {
        val value = ContentValues().apply {
            put(AnimalsDataBase.COLUMN_TABLE_ANIMALS_NAME, animal.name)
            put(AnimalsDataBase.COLUMN_TABLE_ANIMALS_AGE, animal.age)
            put(AnimalsDataBase.COLUMN_TABLE_ANIMALS_BREED, animal.breed)
        }
        writableDatabase.insert(AnimalsDataBase.TABLE_NAME, null, value)
    }

    override suspend fun delete(animal: Animal) {
        val selection = "${AnimalsDataBase.COLUMN_TABLE_ANIMALS_ID} = ?"
        val selectionArgs = arrayOf(animal.id.toString())
        writableDatabase.delete(AnimalsDataBase.TABLE_NAME, selection, selectionArgs)
    }

}