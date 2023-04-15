package com.esmt.projet.victodo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_list.domain.use_case.AddTaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.DeleteTaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.GetTaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
//import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
//import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_onboarding.data.repository.DataStoreRepository
import com.esmt.projet.victodo.feature_task.data.repository.TaskRepositoryImpl
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import com.esmt.projet.victodo.feature_task.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  VictoAppModule {


//DATABASE
    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

//    @Provides
//    @Singleton
//    fun provideMockupRepository(db: MockupDatabase): MockupRepository {
//        return MockupRepositoryImpl(db.mockupDao)
//    }



//REPOSITORY
    @Provides
    @Singleton
    fun provideTaskListRepository(db: TaskDatabase): TaskListRepository {
        return TaskListRepositoryImpl(db.taskListDao)
    }




//USE CASES
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
            deleteTaskUseCase = DeleteTaskUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTaskListUseCases(
        repository: TaskListRepository,
        taskRepository: TaskRepository
    ): TaskListUseCases {
        return TaskListUseCases(
            getTaskList = GetTaskList(repository),
            addTaskList = AddTaskList(repository),
            deleteTaskList = DeleteTaskList(repository, taskRepository)
        )
    }



//DATA STORE
    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}