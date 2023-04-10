package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class MockupGetAllUseCase(
    private val taskRepository: TaskRepository
) {

//    operator fun invoke(): Flow<List<Mockup>> {
//        return mockupRepository.getAll()
//    }
}