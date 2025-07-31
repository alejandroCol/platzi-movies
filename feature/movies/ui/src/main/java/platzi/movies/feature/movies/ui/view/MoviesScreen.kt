package platzi.movies.feature.movies.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import platzi.movies.core.navigation.MovieNavigator
import platzi.movies.feature.movies.ui.presentation.MoviesViewModel
import platzi.movies.feature.movies.ui.view.components.MoviesContent
import platzi.movies.feature.movies.ui.view.components.MoviesTopBar


@OptIn(FlowPreview::class)
@Composable
fun MoviesScreen(viewModel: MoviesViewModel, movieNavigator: MovieNavigator) {
    val movieQuery by viewModel.movieQuery.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val movies = viewModel.movies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        snapshotFlow { searchQuery }
            .debounce(500)
            .collectLatest { query ->
                viewModel.onSearchMovies(query)
            }
    }

    Scaffold(
        topBar = {
            MoviesTopBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                onClearClick = { searchQuery = "" },
                selectedLanguage = movieQuery.language,
                onLanguageSelected = viewModel::setLanguage
            )
        }
    ) { innerPadding ->
        MoviesContent(
            movies = movies,
            modifier = Modifier.padding(innerPadding),
            onMovieClick = { movie ->
                movieNavigator.openMovieDetail(movie.id)
            }
        )
    }
}