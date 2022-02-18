package com.marbleinteractive.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.marbleinteractive.mymovies.databinding.ActivityMainBinding
import com.marbleinteractive.mymovies.model.Movie
import com.marbleinteractive.mymovies.model.MovieDbClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesAdapter = MoviesAdapter(emptyList()){ movie ->
            /*Toast
                .makeText(this@MainActivity, movie.title, Toast.LENGTH_SHORT)
                .show()*/
            navigateTo(movie)
        }

       binding.recycler.adapter = moviesAdapter

        lifecycleScope.launch { //UTILIZAMOS RETROFIT CON CORRUTINAS PARA RECUPERAR LOS DATOS DE LA API
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
            }
        }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java) //Se usa para navegar a una nueva activity
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)

    }


}



