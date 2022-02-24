package com.marbleinteractive.mymovies.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.marbleinteractive.mymovies.R
import com.marbleinteractive.mymovies.databinding.ActivityMainBinding
import com.marbleinteractive.mymovies.model.Movie
import com.marbleinteractive.mymovies.model.MovieDbClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object{
        private const val DEFAULT_REGION = "US"
    }

    private val moviesAdapter = MoviesAdapter(emptyList()){ movie -> navigateTo(movie)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
       requestPopularMovies(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.recycler.adapter = moviesAdapter
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java) //Se usa para navegar a una nueva activity
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    private fun requestPopularMovies(isLocationGranted: Boolean) {
        if(isLocationGranted){
            fusedLocationClient.lastLocation.addOnCompleteListener{
                doRequestPopularMovies(getRegionFromLocation(it.result))
            }
        } else {
        doRequestPopularMovies(DEFAULT_REGION)
        }
        }

    private fun getRegionFromLocation(location: Location?): String {
        if (location==null) return DEFAULT_REGION
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        return result.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }


    private fun doRequestPopularMovies(region: String) {
        lifecycleScope.launch { //UTILIZAMOS RETROFIT CON CORRUTINAS PARA RECUPERAR LOS DATOS DE LA API
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey, region)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
    }
    }

}




