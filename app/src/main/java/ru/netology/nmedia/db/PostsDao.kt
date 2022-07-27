package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.dto.Post

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostsEntity>>

    @Insert
    fun insert(post: PostsEntity)

    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: PostsEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Query("""
        UPDATE posts SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)

    @Query("""
        UPDATE posts SET
        shares = shares +  1,
        shared = 1
        WHERE id = :id
        """)
    fun shareById(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Long)
}