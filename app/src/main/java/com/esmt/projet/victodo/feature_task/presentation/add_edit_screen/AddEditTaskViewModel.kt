package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.exception.task.InvalidTaskException
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_tag.domain.model.InvalidTagException
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.use_case.TagUseCases
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val tagUseCases: TagUseCases,
    private val taskUseCases: TaskUseCases,
    private val listUseCases: TaskListUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _state = mutableStateOf(AddEditTaskState())
    val state : State<AddEditTaskState> = _state

    private val _tagState = mutableStateOf(TagState())
    val tagState : State<TagState> = _tagState

    private val _nameTextFieldState = mutableStateOf(TextFieldState())
    val nameTextFieldState : State<TextFieldState> = _nameTextFieldState

    private val _noteTextFieldState = mutableStateOf(TextFieldState())
    val noteTextFieldState : State<TextFieldState> = _noteTextFieldState

    private val _tagTextField = mutableStateOf(TextFieldState(
        hint = "Search or create a tag"
    ))
    val tagTextField : State<TextFieldState> = _tagTextField

    private var currentTaskId : Long? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _tagColor = mutableStateOf(Color(0xFF006EE9))
    val tagColor : State<Color> = _tagColor

    private var tagJob: Job? = null
    private var listJob: Job? = null



    init {
        getTags()
        getLists()
        savedStateHandle.get<Long>("taskId")?.let { taskId ->
            if (taskId > 0L){
                currentTaskId = taskId
                viewModelScope.launch {
                    taskUseCases.getTaskUseCase(taskId)?.let { taskWithTagAndSubTask ->
                        _nameTextFieldState.value = _nameTextFieldState.value.copy(
                            text = taskWithTagAndSubTask.task.name
                        )
                        _noteTextFieldState.value = _noteTextFieldState.value.copy(
                            text = taskWithTagAndSubTask.task.note
                        )
                        _state.value = state.value.copy(
                            priority = taskWithTagAndSubTask.task.priority,
                            dueDate = taskWithTagAndSubTask.task.dueDate,
                            dueTime = taskWithTagAndSubTask.task.dueTime,
                            listId = taskWithTagAndSubTask.task.listId,
                            repeatFrequency = taskWithTagAndSubTask.task.redundancy,
                            showDeadlineOptions = taskWithTagAndSubTask.task.dueDate != null
                        )
                        _tagState.value = tagState.value.copy(
                            selectedTags = taskWithTagAndSubTask.tags.toMutableList()
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.EnteredName -> {
                _nameTextFieldState.value = _nameTextFieldState.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.EnteredNote -> {
                _noteTextFieldState.value = _noteTextFieldState.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.EnteredPriority -> {
                _state.value = _state.value.copy(
                    priority = event.value
                )
            }
            is AddEditTaskEvent.EnteredDueDate -> {
                _state.value = _state.value.copy(
                    dueDate = event.value
                )
            }
            is AddEditTaskEvent.EnteredDueTime -> {
                _state.value = _state.value.copy(
                    dueTime = event.value
                )
            }
            is AddEditTaskEvent.EnteredListId -> {
                _state.value = _state.value.copy(
                    listId = event.value
                )
            }
            is AddEditTaskEvent.EnteredTag -> {
//                _tagState.value.selectedTags.add(event.value)
                _tagState.value = tagState.value.copy(
                    selectedTags = tagState.value.selectedTags.toMutableList().apply {
                        add(event.value)
                    }
                )
            }
            is AddEditTaskEvent.RemovedTag -> {
//                _tagState.value.selectedTags.remove(event.value)
                _tagState.value = tagState.value.copy(
                    selectedTags = tagState.value.selectedTags.toMutableList().apply {
                        remove(event.value)
                    }
                )
            }
            is AddEditTaskEvent.EnteredRedundancy -> {
                _state.value = _state.value.copy(
                    repeatFrequency = event.value
                )
            }
            is AddEditTaskEvent.SaveTask -> {
                Log.d("AddEditTaskViewModel", "SaveTask")
                viewModelScope.launch {
                    Log.d("AddEditTaskViewModel", "SaveTask launched")
                    try {
                        Log.d("AddEditTaskViewModel", "SaveTask:§")
                        Log.i("AddEditTaskViewModel", "listId: ${state.value.listId}")
                        taskUseCases.addTaskUseCase(
                            TaskWithTagAndSubTask(
                                task = Task(
                                    id = currentTaskId,
                                    name = nameTextFieldState.value.text,
                                    note = noteTextFieldState.value.text,
                                    priority = state.value.priority,
                                    dueDate = if(state.value.showDeadlineOptions) state.value.dueDate else null,
                                    dueTime = if(state.value.showDeadlineOptions) state.value.dueTime else null,
                                    listId = state.value.listId,
                                    redundancy = if (state.value.showDeadlineOptions) state.value.repeatFrequency else Task.Companion.RepeatFrequency.NEVER.value,
                                ),
                                tags = tagState.value.selectedTags,
                            ),
                            context
                        )
                        Log.d("AddEditTaskViewModel", "repository called")
                        _eventFlow.emit(UiEvent.SaveTask)
                        Log.d("AddEditTaskViewModel", "event emitted")
                    }catch (e: InvalidTaskException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message ?: "An error occurred"))
                    }
                }
            }
            is AddEditTaskEvent.ToggleDeadlineOptions -> {
                _state.value = state.value.copy(
                    showDeadlineOptions = !state.value.showDeadlineOptions
                )
            }
            is AddEditTaskEvent.CreateTag -> {
                viewModelScope.launch {
                    if(_tagState.value.tags.any {
                            it.title.contains(
                                _tagTextField.value.text,
                                ignoreCase = true
                            )
                        }){
                        _eventFlow.emit(UiEvent.ShowSnackBar("Tag already exists"))
                        return@launch
                    }
                    try {
                        val id = tagUseCases.addTagUseCase(
                            Tag(
                                title = _tagTextField.value.text
                            )
                        )
                        _tagState.value.selectedTags.add(
                            Tag(
                                id = id,
                                title = _tagTextField.value.text
                            )
                        )
                        _tagTextField.value = _tagTextField.value.copy(
                            text = ""
                        )
                    }catch (e: InvalidTagException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message ?: "An error occurred"))
                    }
                }
            }
            is AddEditTaskEvent.EnteredTagText -> {
                _tagTextField.value = _tagTextField.value.copy(
                    text = event.value
                )
                _tagState.value = _tagState.value.copy(
                    tagList = tagState.value.tags.filter {
                        it.title.contains(event.value, ignoreCase = true)
                    }
                )
            }
            is AddEditTaskEvent.TagTextFieldFocused -> {
                _tagTextField.value = _tagTextField.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _tagTextField.value.text.isBlank()
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveTask : UiEvent()
    }

    private fun getTags() {
        tagJob?.cancel()
        tagJob = tagUseCases.getAllTagUseCase().onEach {
            _tagState.value = tagState.value.copy(
                tags = it,
                tagList = it
            )
        }.launchIn(viewModelScope)
    }

    private fun getLists() {
        listJob?.cancel()
        listJob = listUseCases.getListsUseCase().onEach {
            _state.value = state.value.copy(
                tasklists = it
            )
        }.launchIn(viewModelScope)
    }
}