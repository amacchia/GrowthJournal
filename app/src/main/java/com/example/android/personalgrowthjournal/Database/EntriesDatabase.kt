package com.example.android.personalgrowthjournal.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(Entry::class), version = 1, exportSchema = false)
abstract class EntriesDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    // Kt classes cannot have static methods, so use companion object for singleton
    companion object {

        val DATABASE_NAME = "journal-db"

        // For Singleton instantiation
        @Volatile
        private var instance: EntriesDatabase? = null


        fun getInstance(context: Context?): EntriesDatabase {
            return if (instance != null) instance!! else buildDatabase(context)
        }

        private fun buildDatabase(context: Context?): EntriesDatabase {
            return Room.databaseBuilder(
                context!!,
                EntriesDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

    }
}