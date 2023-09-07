package com.alwihbsyi.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alwihbsyi.githubuser.data.local.entity.UserFavEntity

@Database(entities = [UserFavEntity::class], version = 1)
abstract class UserFavDatabase: RoomDatabase() {

    abstract fun userDao(): UserFavDao

    companion object {
        @Volatile
        private var INSTANCE: UserFavDatabase? = null

        fun getInstance(context: Context): UserFavDatabase {
            if (INSTANCE == null) {
                synchronized(UserFavDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserFavDatabase::class.java, "fav_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as UserFavDatabase
        }
    }

}