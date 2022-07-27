package ru.netology.nmedia.db

object PostsTable {

    const val NAME = "Posts"

    val DDL = """
        CREATE TABLE $NAME (
            ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.AUTHOR.columnName} TEXT NOT NULL,
            ${Column.CONTENT.columnName} TEXT NOT NULL,
            ${Column.PUBLISHED.columnName} TEXT NOT NULL,
            ${Column.VIDEOURL.columnName} TEXT,
            ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 999,
            ${Column.SHARES.columnName} INTEGER NOT NULL DEFAULT 99990,
            ${Column.VIEWS.columnName} INTEGER NOT NULL DEFAULT 5,
            ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
            ${Column.SHARED.columnName} BOOLEAN NOT NULL DEFAULT 0
        );
        """.trimIndent()

    val ALL_COLUMNS_NAME = Column.values().map{
        it.columnName
    }.toTypedArray()

    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        VIDEOURL("videoUrl"),
        LIKES("likes"),
        SHARES("shares"),
        VIEWS("views"),
        LIKED_BY_ME("likedByMe"),
        SHARED("shared")
    }

}