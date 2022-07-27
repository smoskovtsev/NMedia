package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val videoUrl: String? = "",
    var likes: Int = 999,
    var shares: Int = 99990,
    var views: Int = 5,
    var likedByMe: Boolean,
    var shared: Boolean
)
