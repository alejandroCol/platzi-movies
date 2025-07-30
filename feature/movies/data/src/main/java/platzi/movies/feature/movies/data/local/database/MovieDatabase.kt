package platzi.movies.feature.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import platzi.movies.feature.movies.data.local.dao.MovieDao
import platzi.movies.feature.movies.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}