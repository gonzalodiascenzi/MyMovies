package com.marbleinteractive.mymovies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marbleinteractive.mymovies.databinding.ViewMovieItemBinding
import com.marbleinteractive.mymovies.model.Movie

/*interface MovieClickListener{
    fun onMovieClicked(movie: Movie) // (Movie) -> Unit
}*/

class MoviesAdapter(
    var movies: List<Movie>,
    private val movieClickListener: (Movie) -> Unit)
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      /* val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_movie_item, parent, false)

       */

        val  binding = ViewMovieItemBinding.inflate(
            LayoutInflater
                .from(parent.context),
                parent,
                false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener{ movieClickListener(movie)}
    }

    override fun getItemCount(): Int {
       return movies.size
    }

    //class  ViewHolder(view: View): RecyclerView.ViewHolder(view)

    class  ViewHolder(private val binding: ViewMovieItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
            binding.titulo.text = movie.title
            Glide
                .with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                .into(binding.portada)
        }
    }
}