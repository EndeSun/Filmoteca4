package es.ua.eps.filmoteca

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import es.ua.eps.filmoteca.databinding.ActivityFilmEditBinding
class FilmEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.save.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.selectImage.setOnClickListener {
            val photoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(photoIntent)
        }

        binding.catchPhoto.setOnClickListener {
            val cameraIntent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
        }

    }
}


