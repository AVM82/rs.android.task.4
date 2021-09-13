package org.rsschool.rsandroidtask4.di

import android.app.Application
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rsschool.rsandroidtask4.repository.Repository
import org.rsschool.rsandroidtask4.repository.room.AnimalsDataBaseRoom
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDb(application: Application): AnimalsDataBaseRoom = databaseBuilder(
        application, AnimalsDataBaseRoom::class.java, "animals_db"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun repo(db: AnimalsDataBaseRoom) = Repository(db)

}
