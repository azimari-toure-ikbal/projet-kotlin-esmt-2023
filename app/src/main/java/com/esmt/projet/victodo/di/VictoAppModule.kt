package com.esmt.projet.victodo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_list.domain.use_case.AddTaskListUseCase
import com.esmt.projet.victodo.feature_list.domain.use_case.DeleteTaskListUseCase
import com.esmt.projet.victodo.feature_list.domain.use_case.GetTaskListUseCase
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
//import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
//import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_onboarding.data.repository.DataStoreRepository
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

//REPOSITORY
    @Provides
    @Singleton
    fun provideTaskListRepository(db: TaskDatabase): TaskListRepository {
        return TaskListRepositoryImpl(db.taskListDao)
    }




//USE CASES


    @Provides
    @Singleton
    fun provideTaskListUseCases(
        repository: TaskListRepository,
        taskRepository: TaskRepository
    ): TaskListUseCases {
        return TaskListUseCases(
            getTaskListUseCase = GetTaskListUseCase(repository),
            addTaskListUseCase = AddTaskListUseCase(repository),
            deleteTaskListUseCase = DeleteTaskListUseCase(repository, taskRepository)
        )
    }



//DATA STORE
    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}