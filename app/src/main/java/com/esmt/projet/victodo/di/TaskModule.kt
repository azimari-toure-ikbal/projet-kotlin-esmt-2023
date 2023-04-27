package com.esmt.projet.victodo.di

import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
import com.esmt.projet.victodo.feature_task.data.repository.TaskRepositoryImpl
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import com.esmt.projet.victodo.feature_task.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {
    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            addTaskUseCase = AddTaskUseCase(repository),
            getTasksUseCase = GetTasksUseCase(repository),
            getTaskUseCase = GetTaskUseCase(repository),
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            getCompletedTasksUseCase = GetCompletedTasksUseCase(repository),
            getScheduledTasksUseCase = GetScheduledTasksUseCase(repository),
            getLateTasksUseCase = GetLateTasksUseCase(repository),
            getTodayTasksUseCase = GetTodayTasksUseCase(repository),
            getTasksByListIdUseCase = GetTasksByListIdUseCase(repository),
        )
    }
}