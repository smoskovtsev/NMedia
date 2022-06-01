package ru.netology.nmedia.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String?,
    val content: String?,
    val published: String?,
    val videoUrl: String? = "",
    var likes: Int = 999,
    var shares: Int = 99990,
    var views: Int = 5,
    var likedByMe: Boolean = false,
    var shared: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(published)
        parcel.writeString(videoUrl)
        parcel.writeInt(likes)
        parcel.writeInt(shares)
        parcel.writeInt(views)
        parcel.writeByte(if (likedByMe) 1 else 0)
        parcel.writeByte(if (shared) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}
