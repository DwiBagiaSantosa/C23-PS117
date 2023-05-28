package com.example.capstone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.capstone.models.User
import com.example.capstone.repositories.UserDao

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val migration1to2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE users ADD COLUMN gender TEXT NOT NULL DEFAULT ''")
                        database.execSQL("ALTER TABLE users ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
                        database.execSQL("ALTER TABLE users ADD COLUMN height REAL NOT NULL DEFAULT 0.0")
                        database.execSQL("ALTER TABLE users ADD COLUMN weight REAL NOT NULL DEFAULT 0.0")
                    }
                }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "CS23-PS117-Bangkit"
                )
                    .addMigrations(migration1to2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
