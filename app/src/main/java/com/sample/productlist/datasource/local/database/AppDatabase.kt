package com.sample.productlist.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.productlist.datasource.local.database.dao.CommentDao
import com.sample.productlist.datasource.local.database.dao.IssueDao
import com.sample.productlist.datasource.local.database.entity.CommentEntity
import com.sample.productlist.datasource.local.database.entity.IssueEntity

@Database(entities = [IssueEntity::class, CommentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao
    abstract fun commentDao(): CommentDao
}
