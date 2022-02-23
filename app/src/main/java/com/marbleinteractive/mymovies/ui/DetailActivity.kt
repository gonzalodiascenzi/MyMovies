package com.marbleinteractive.mymovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bumptech.glide.Glide
import com.marbleinteractive.mymovies.databinding.ActivityDetailBinding
import com.marbleinteractive.mymovies.model.Movie



class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if(movie!=null){
            title = movie.title
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                .into(binding.backdrop)
            binding.summary.text = movie.overview + movie.overview + movie.overview + movie.overview + movie.overview
            bindDetailInfo(binding.detailInfo, movie)
        }
        binding.fav.setOnClickListener{
            val message = "Pelicula guardada en Favoritos"
            Toast.makeText(this@DetailActivity, message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun bindDetailInfo(detailInfo: TextView, movie: Movie) {
     detailInfo.text = buildSpannedString {
         bold { append("Original language: ") }
         appendLine(movie.original_language)

         bold { append("Original title: ") }
         appendLine(movie.original_title)

         bold { append("Release Date: ") }
         appendLine(movie.release_date)

         bold { append("Popularity ") }
         appendLine(movie.popularity.toString())

         bold { append("Vote Average: ") }
         appendLine(movie.vote_average.toString())
     }
    }
}

