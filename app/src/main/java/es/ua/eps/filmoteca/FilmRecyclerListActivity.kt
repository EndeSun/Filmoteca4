package es.ua.eps.filmoteca

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.ua.eps.filmoteca.databinding.ActivityFilmRecyclerListBinding

class FilmAdapter(val film: List<FilmListActivity.Film>) : RecyclerView.Adapter<FilmAdapter.ViewHolder?>() {

    private var listener: (position: Int) -> Unit = {}
    fun setOnItemClickListener(listener: (position:Int) -> Unit) {
        this.listener = listener // Guardamos una referencia al listener
    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var title: TextView
        private var director: TextView
        private var filmImage: ImageView

        fun bind(l: FilmListActivity.Film) {
            title.text = l.title
            director.text = l.director
            filmImage.setImageResource(l.imageResId)
        }
        init {
            title = v.findViewById(R.id.filmTitle)
            director = v.findViewById(R.id.filmDirector)
            filmImage = v.findViewById(R.id.ivList)
        }
    }
//    Método para crear los diferentes items de la lista, hace referencia al layout de los items
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_film2, parent, false)
    val holder = ViewHolder(view)

    view.setOnClickListener {
        val position: Int = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener(position)
        }
    }

    return holder
}

    //        Número total de datos que hay en la lista
    override fun getItemCount(): Int = FilmListActivity.FilmDataSource.films.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Muestra los datos en la posición indicada
        holder.bind(FilmListActivity.FilmDataSource.films[position])
    }
}
class FilmRecyclerListActivity: AppCompatActivity(){
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_recycler_list)
        val binding = ActivityFilmRecyclerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Donde se pita la lista
        recyclerView = findViewById(R.id.listaRecycler)
//        Un manejador de layout lineal
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)

        val adapter = FilmAdapter(FilmListActivity.FilmDataSource.films)
//        Asignamos el adaptaddor que hemos creado
        recyclerView?.adapter = adapter
        this.adapter = adapter

        adapter.setOnItemClickListener {
            position ->  goFilm(position)
        }


    }
    private fun goFilm(position: Int){
        val intentFilm = Intent(this@FilmRecyclerListActivity, FilmDataActivity::class.java)
        intentFilm.putExtra("position", position)
        startActivity(intentFilm)
    }
}