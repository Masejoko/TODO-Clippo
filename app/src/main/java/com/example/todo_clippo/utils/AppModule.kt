package com.example.todo_clippo.utils

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val appModule = module {
        single { AppDatabase.getInstance(get()) }
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "todo.db")
            .fallbackToDestructiveMigration()
            .build() }
        single { get<AppDatabase>().toDoDao() }
        viewModel { TaskViewModel(get()) }
    }
