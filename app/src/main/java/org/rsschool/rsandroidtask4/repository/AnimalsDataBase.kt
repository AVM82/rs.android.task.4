package org.rsschool.rsandroidtask4.repository

import org.rsschool.rsandroidtask4.data.AnimalsDAO

interface AnimalsDataBase {
    val animalsDAO: AnimalsDAO

    companion object {
        const val DATABASE_NAME = "animals_db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "animals"
        const val COLUMN_TABLE_ANIMALS_ID = "id"
        const val COLUMN_TABLE_ANIMALS_NAME = "name"
        const val COLUMN_TABLE_ANIMALS_AGE = "age"
        const val COLUMN_TABLE_ANIMALS_BREED = "breed"
        const val CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                " $COLUMN_TABLE_ANIMALS_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_TABLE_ANIMALS_NAME TEXT," +
                " $COLUMN_TABLE_ANIMALS_AGE REAL," +
                " $COLUMN_TABLE_ANIMALS_BREED TEXT);"
    }
}
