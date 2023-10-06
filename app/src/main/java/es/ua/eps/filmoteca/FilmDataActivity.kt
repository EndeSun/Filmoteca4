package es.ua.eps.filmoteca

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import es.ua.eps.filmoteca.databinding.ActivityFilmDataBinding


class FilmDataActivity : AppCompatActivity() {

    private val MOVIE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receiveIntent = intent
        val movieA = receiveIntent.getStringExtra("filmA")
        val movieB = receiveIntent.getStringExtra("filmB")

        if(movieA != null){
            binding.filmData.text = movieA
        }else{
            binding.filmData.text = movieB
        }

        binding.IMDBLink.setOnClickListener {
            val filmDataIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/tt0083658/?ref_=fn_al_tt_1"))
            startActivity(filmDataIntent)
        }


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
                filmData.text = getString(R.string.filmEdited)
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                filmData.text = getString(R.string.filmCancel)
            }
        }
    }
}