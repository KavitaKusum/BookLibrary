package com.example.booklibrary.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.booklibrary.R
import com.example.booklibrary.domain.model.Book
import com.example.booklibrary.ui.UIState
import com.example.booklibrary.ui.viewmodels.BookListViewModel

private fun getCoverImageUrl(coverId: Int): String {
    return "https://covers.openlibrary.org/b/id/$coverId-L.jpg"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(viewModel: BookListViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedBook by viewModel.selectedBook.collectAsState()
    if (selectedBook != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onDismissBottomSheet() },
            sheetState = rememberModalBottomSheetState()
        ) {
            BookDetailsSheetContent(book = selectedBook!!)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UIState.Loading -> {
                LoadingIndicator()
            }

            is UIState.Success -> {
                BookList(
                    books = state.data,
                    onBookClick = { book ->
                        viewModel.onBookSelected(book)
                    }
                )
            }

            is UIState.Error -> {
                ErrorView(message = state.message, onRetry = { viewModel.fetchBooks() })
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("LoadingIndicator")
        )
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun BookList(books: List<Book>, onBookClick: (Book) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(books) { book ->
            BookItem(book = book, onBookClick = { onBookClick(book) })
        }
    }
}

@Composable
fun BookItem(book: Book, onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBookClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(getCoverImageUrl(book.coverId))
                .crossfade(true)
                .size(coil3.size.Size(240, 360))
                .build()

            AsyncImage(
                model = imageRequest,
                contentDescription = book.title,
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.image_not_found)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "by ${book.authorNames.joinToString()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun BookDetailsSheetContent(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = getCoverImageUrl(book.coverId),
            contentDescription = book.title,
            modifier = Modifier
                .height(300.dp),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.image_not_found)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "By: ${book.authorNames.joinToString()}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
