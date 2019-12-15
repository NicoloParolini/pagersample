package com.livingcode.test.celotest.storage.database

import android.content.Context
import androidx.paging.DataSource
import androidx.room.*
import com.livingcode.test.celotest.storage.models.User
import io.reactivex.Completable

@Database(entities = [User::class], version = 3)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        fun create(context: Context, useInMemory: Boolean): UserDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            } else {
                Room.databaseBuilder(context, UserDatabase::class.java, "UserDatabase.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM users WHERE name_first LIKE :search OR name_last LIKE :search")
    fun getFilteredUsers(search: String): DataSource.Factory<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>): Completable
}