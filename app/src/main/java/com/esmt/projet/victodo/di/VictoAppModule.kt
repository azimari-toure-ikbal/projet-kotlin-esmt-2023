package com.esmt.projet.victodo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.esmt.projet.victodo.core.data.data_source.TaskDatabase
//import com.esmt.projet.victodo.feature_list.data.repository.TaskListRepositoryImpl
//import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import com.esmt.projet.victodo.feature_list.domain.use_case.AddTaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.DeleteTaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.GetTaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_onboarding.data.repository.DataStoreRepository
import com.esmt.projet.victodo.feature_task.domain.repository.MockupRepository
import com.esmt.projet.victodo.feature_task.domain.use_case.MockupGetAllUseCase
import com.esmt.projet.victodo.feature_task.domain.use_case.MockupUseCases
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
    fun provideMockupDatabase(app: Application): TaskDatabase {
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
//    @Provides
//    @Singleton
//    fun provideTaskListRepository(db: TaskDatabase): TaskListRepository {
//        return TaskListRepositoryImpl(db.taskListDao)
//    }




//USE CASES
    @Provides
    @Singleton
    fun provideMockupUseCases(repository: MockupRepository): MockupUseCases {
        return MockupUseCases(
            getAll = MockupGetAllUseCase(repository)
        )
    }

//    @Provides
//    @Singleton
//    fun provideTaskListUseCases(repository: TaskListRepository): TaskListUseCases {
//        return TaskListUseCases(
//            getTaskList = GetTaskList(repository),
//            addTaskList = AddTaskList(repository),
//            deleteTaskList = DeleteTaskList(repository)
//        )
//    }



//DATA STORE
    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}