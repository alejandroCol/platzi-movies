@Composable
fun MoviesContent(
    movies: LazyPagingItems<Movie>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    when (movies.loadState.refresh) {
        is LoadState.Loading -> LoadingState()
        is LoadState.Error -> {
            val errorState = movies.loadState.refresh as LoadState.Error
            ErrorState(errorState.error.localizedMessage)
        }
        is LoadState.NotLoading -> {
            if (movies.itemCount == 0) {
                EmptyState()
            } else {
                LazyColumn(modifier = modifier.padding(16.dp)) {
                    items(movies.itemCount) { index ->
                        movies[index]?.let { movie ->
                            MovieItem(movie = movie, navController = navController)
                        }
                    }
                }
            }
        }
    }
}