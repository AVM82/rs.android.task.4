package org.rsschool.rsandroidtask4.di

import android.app.Application
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rsschool.rsandroidtask4.repository.AnimalsDataBase
import org.rsschool.rsandroidtask4.repository.Repository
import org.rsschool.rsandroidtask4.repository.cursor.AnimalsDataBaseCursor
import org.rsschool.rsandroidtask4.repository.room.AnimalsDataBaseRoom
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDb(application: Application): AnimalsDataBaseRoom = databaseBuilder(
        application,
        AnimalsDataBaseRoom::class.java,
        AnimalsDataBase.DATABASE_NAME
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCursorDb(application: Application): AnimalsDataBaseCursor = AnimalsDataBaseCursor(application)

    @Singleton
    @Provides
    fun repo(db: AnimalsDataBaseRoom) = Repository(db)
//    fun repo(db: AnimalsDataBaseCursor) = Repository(db)

}
