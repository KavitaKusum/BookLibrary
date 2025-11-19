package com.example.booklibrary.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.domain.usecase.ListUseCase
import com.example.booklibrary.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val useCase: ListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Book>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Book>>> = _uiState
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchBooks()
    }

    fun fetchBooks() {
        _uiState.value = UIState.Loading
        val disposable = useCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { books ->
                    _uiState.value = UIState.Success(books)
                },
                { error ->
                    _uiState.value = UIState.Error(error.message ?: "An unknown error occurred")
                }
            )
        compositeDisposable.add(disposable)
    }

    fun onBookSelected(book: Book) {
        _selectedBook.value = book
    }

    fun onDismissBottomSheet() {
        _selectedBook.value = null
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}