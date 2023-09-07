package com.alwihbsyi.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("favorite_user")
@Parcelize
data class UserFavEntity(
    @ColumnInfo("name")
    var username: String? = null,

    @ColumnInfo("username")
    @PrimaryKey
    var login: String = "",

    @ColumnInfo("profile_pic")
    var avatar_url: String? = null
): Parcelable