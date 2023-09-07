package com.alwihbsyi.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alwihbsyi.githubuser.data.local.entity.UserFavEntity

@Dao
interface UserFavDao {
    @Query("SELECT  * from favorite_user")
    fun getAllFavorite(): LiveData<List<UserFavEntity>>

    @Query("SELECT * from favorite_user WHERE username = :username")
    fun getUserInfo(username: String): LiveData<List<UserFavEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: UserFavEntity)

    @Update
    fun update(fav: UserFavEntity)

    @Delete
    fun delete(fav: UserFavEntity)

}