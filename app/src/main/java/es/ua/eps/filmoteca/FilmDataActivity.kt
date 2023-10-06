package es.ua.eps.filmoteca

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.component1
import es.ua.eps.filmoteca.databinding.ActivityFilmDataBinding


class FilmDataActivity : AppCompatActivity() {

    private val MOVIE = 123
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityFilmDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Intent que recibe los extras
        val receiveIntent = intent
        val position = receiveIntent.getIntExtra("position", 0)

        binding.filmData.text = FilmListActivity.FilmDataSource.films[position].title
        binding.nameDirectorBladeRunner.text = FilmListActivity.FilmDataSource.films[position].director
        binding.yearPublicationBladeRunner.text = FilmListActivity.FilmDataSource.films[position].year.toString()

        val resources: Resources = resources
        val genderOptions = resources.getStringArray(R.array.genderOption)
        if (FilmListActivity.FilmDataSource.films[position].genre != null) {
            binding.filmGenderBladeRunner.text = genderOptions[FilmListActivity.FilmDataSource.films[position].genre]
        }

        val formatOptions = resources.getStringArray(R.array.formatOption)
        if(FilmListActivity.FilmDataSource.films[position].format != null){
            binding.filmFormatBladeRunner.text = formatOptions[FilmListActivity.FilmDataSource.films[position].format]
        }

        binding.IMDBLink.setOnClickListener {
            val filmDataIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FilmListActivity.FilmDataSource.films[position].imdbUrl))
            startActivity(filmDataIntent)
        }

        binding.bladeRunnerImage.setImageResource(FilmListActivity.FilmDataSource.films[position].imageResId)


        //Cuando se le envia al film edit
        binding.filmEdit.setOnClickListener {
            val filmEditIntent = Intent(this@FilmDataActivity, FilmEditActivity::class.java)
            if(Build.VERSION.SDK_INT >= 30) {
                startForResult.launch(filmEditIntent)
            }
            else {
                @Suppress("DEPRECATION")
                startActivityForResult(filmEditIntent, MOVIE)
            }
        }

        binding.backToHome.setOnClickListener {
            val backToHomeIntent = Intent(this@FilmDataActivity, FilmListActivity::class.java)
            backToHomeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(backToHomeIntent)
        }
    }

    private val startForResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                onActivityResult(MOVIE, result.resultCode, result.data)
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        val filmData = findViewById<TextView>(R.id.filmData)
        if(requestCode == MOVIE){
            if (resultCode == Activity.RESULT_OK){
                FilmListActivity.FilmDataSource.films[position].title = data?.getStringExtra("inputFilmTitle")
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                filmData.text = getString(R.string.filmCancel)
            }
        }
    }
}