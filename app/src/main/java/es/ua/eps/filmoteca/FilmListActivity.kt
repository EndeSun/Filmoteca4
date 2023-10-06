package es.ua.eps.filmoteca

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import es.ua.eps.filmoteca.databinding.ActivityFilmListBinding



class FilmListActivity : AppCompatActivity() {
    class Film {
        var imageResId = 0 // Propiedades de la clase
        var title: String? = null
        var director: String? = null
        var year = 0
        var genre = 0
        var format = 0
        var imdbUrl: String? = null
        var comments: String? = null

        override fun toString(): String {
            return title?:"<Sin titulo>" // Al convertir a cadena mostramos su título
        }

        companion object {
            const val FORMAT_DVD = 0 // Formatos
            const val FORMAT_BLURAY = 1
            const val FORMAT_DIGITAL = 2
            const val GENRE_ACTION = 0 // Géneros
            const val GENRE_COMEDY = 1
            const val GENRE_DRAMA = 2
            const val GENRE_SCIFI = 3
            const val GENRE_HORROR = 4
        }
    }

    object FilmDataSource {
        val films: MutableList<Film> = mutableListOf<Film>()

        init {
            val f1 = Film()
            f1.title = "Blade Runner"
            f1.director = "Ridley Scott"
            f1.imageResId = R.mipmap.ic_launcher
            f1.comments = ""
            f1.format = Film.FORMAT_DIGITAL
            f1.genre = Film.GENRE_SCIFI
            f1.imdbUrl = "https://www.imdb.com/title/tt0083658/?ref_=fn_al_tt_1"
            f1.year = 1982


            val f2 = Film()
            f2.title = "Regreso al futuro II"
            f2.director = "Robert Zemeckis"
            f2.imageResId = R.mipmap.ic_launcher
            f2.comments = ""
            f2.format = Film.FORMAT_DVD
            f2.genre = Film.GENRE_SCIFI
            f2.imdbUrl = "https://www.imdb.com/title/tt0096874/?ref_=fn_al_tt_3"
            f2.year = 1989

            val f3 = Film()
            f3.title = "Regreso al futuro III"
            f3.director = "Robert Zemeckis"
            f3.imageResId = R.mipmap.ic_launcher
            f3.comments = ""
            f3.format = Film.FORMAT_BLURAY
            f3.genre = Film.GENRE_ACTION
            f3.imdbUrl = "https://www.imdb.com/title/tt0099088/?ref_=fn_al_tt_4"
            f3.year = 1990

            val f4 = Film()
            f4.title = "Los cazafantasmas"
            f4.director = "Ivan Reitman"
            f4.imageResId = R.mipmap.ic_launcher
            f4.comments = ""
            f4.format = Film.FORMAT_DIGITAL
            f4.genre = Film.GENRE_SCIFI
            f4.imdbUrl = "https://www.imdb.com/title/tt4513678/"
            f4.year = 1984

            films.add(f1)
            films.add(f2)
            films.add(f3)
            films.add(f4)
        }
    }

    class FilmsArrayAdapter (context: Context?, resource:Int, objects: List<Film>): ArrayAdapter<Film>(context!!, resource, objects!!){
        override fun getView(position: Int, convertView: View?, parent:ViewGroup) : View{
            val view: View = convertView?: LayoutInflater.from(this.context).inflate(R.layout.item_film, parent, false)

            val tvFilmName = view.findViewById(R.id.filmName) as TextView
            val tvDirectorName = view.findViewById(R.id.directorName) as TextView
            val ivFilmIcon = view.findViewById(R.id.filmIcon) as ImageView

            getItem(position) ?.let {
                tvFilmName.text = it.title
                tvDirectorName.text = it.director
                ivFilmIcon.setImageResource(it.imageResId)
            }
            return view
        }
    }

//    Para crear listas: Tener el array , tener el layout definido(del sistema o propio) donde irá cada elemento, tener el adaptador(del sistema o propio hay que crear una clase de ArrayAdapter),
//    y une la referencia del layout con el dato del object que se quiere rellenar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listFilm = binding.filmList

//        Este adatador predefinido ya no nos sirve, necesitamos crear el nuestro propio
        val adapter = FilmsArrayAdapter(
            this,
            R.layout.item_film,
            FilmDataSource.films
        )

        listFilm.adapter = adapter

        listFilm.setOnItemClickListener{ parent: AdapterView<*>, view: View,
                                     position: Int, id: Long ->
            val elemento = adapter.getItem(position) as String
            Toast.makeText(
                this@FilmListActivity,
                "$elemento seleccionado", Toast.LENGTH_LONG
          ).show()
         }

    /*       binding.filmA.setOnClickListener {
               val filmAListIntent = Intent(this@FilmListActivity, FilmDataActivity::class.java)

               filmAListIntent.putExtra("filmA", getString(R.string.bladeRunner))
               startActivity(filmAListIntent)
           }

           binding.filmB.setOnClickListener {
               val filmBListIntent = Intent(this@FilmListActivity, FilmDataActivity::class.java)
               filmBListIntent.putExtra("filmB", getString(R.string.watchFilmB))
               startActivity(filmBListIntent)
           }

           binding.about.setOnClickListener {
               val aboutIntent = Intent(this@FilmListActivity, AboutActivity::class.java)
               startActivity(aboutIntent)
           }*/
    }
}