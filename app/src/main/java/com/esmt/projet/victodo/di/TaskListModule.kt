package com.esmt.projet.victodo.di

import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_list.domain.use_case.*
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskListModule {

    @Provides
    @Singleton
    fun provideTaskListRepository(db: TaskDatabase): TaskListRepository {
        return TaskListRepositoryImpl(db.taskListDao)
    }

    @Provides
    @Singleton
    fun provideTaskListUseCases(
        repository: TaskListRepository,
        taskRepository: TaskRepository
    ): TaskListUseCases {
        return TaskListUseCases(
            addTaskListUseCase = AddTaskListUseCase(repository),
            getTaskListsUseCase = GetTaskListsUseCase(repository),
            deleteTaskListUseCase = DeleteTaskListUseCase(repository, taskRepository),
            searchTaskList = SearchTaskListsUseCase(repository),
            getTaskListUseCase = GetTaskListUseCase(repository),
            getUtilTaskListsUseCase = GetUtilTaskListsUseCase(repository, taskRepository),
            getListsUseCase = GetListsUseCase(repository)
        )
    }

}