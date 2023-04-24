package com.example.todo_clippo.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo_clippo.model.ToDoModel

/**
 * AppDatabase class for managing the Room database.
 *
 * This class is annotated with the @Database annotation which includes the entities
 * and version number of the database schema.
 */
@Database(entities = [ToDoModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the ToDoDao.
     *
     * @return An instance of ToDoDao.
     */
    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retrieves the singleton instance of the AppDatabase.
         *
         * @param context The context used for creating the database.
         * @return The singleton instance of the AppDatabase.
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
