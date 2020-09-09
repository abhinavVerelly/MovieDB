package com.example.t04

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListFragment : Fragment() {

    var sortButton: Button? = null
    var showLiked: Boolean = false
    var fam: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_list, container, false)


        val recyclerView = v.findViewById<RecyclerView>(R.id.movie_list)
        val adapter = MovieListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val model = ViewModelProviders.of(this).get(MovieViewModel::class.java)


        (v.findViewById<Button>(R.id.sort_rating)).setOnClickListener{

            if (fam == false){
                adapter.sortRating()
                fam = true
            } else {
                adapter.sortRating2()
                fam = false
            }



        }

        (v.findViewById<Button>(R.id.sort_title)).setOnClickListener{

            sortButton?.text="Sort With Title"

            if (fam == false){
                adapter.sortTitle()
                fam = true
            } else {
                adapter.sortTitle2()
                fam = false
            }
            //adapter.sortTitle()

        }




        (v.findViewById<Button>(R.id.show_liked_button)).setOnClickListener {
            showLiked = true
            adapter.displayLiked()
        }
        (v.findViewById<Button>(R.id.all)).setOnClickListener {
            model.deleteAll()
            model.refreshMovies(1)
            model.refreshMovies(2)
            model.refreshMovies(3)
        }











        model.allMovies.observe(
            viewLifecycleOwner,
            Observer<List<MovieItem>>{ movies ->
                movies?.let{
                    adapter.setMovies(it)
                }
            }
        )

        (v.findViewById<Button>(R.id.refresh)).setOnClickListener{
            model.deleteAll()
            model.refreshMovies(1)
            model.refreshMovies(2)
            model.refreshMovies(3)
        }

    return v

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    inner class MovieListAdapter():
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){
        private var movies = emptyList<MovieItem>()
        ////
        internal fun setMovies(movies: List<MovieItem>) {
            this.movies = movies
            notifyDataSetChanged()
        }


        fun displayLiked() {

            movies = movies.filter{it.liked==true}

            notifyDataSetChanged()

        }

         fun sortRating(){
            movies = movies.sortedWith(compareByDescending({it.vote_average}))
            notifyDataSetChanged()
        }
        fun sortRating2(){
            movies = movies.sortedBy { it.vote_average }
            notifyDataSetChanged()
        }

        fun sortTitle(){
            movies = movies.sortedWith(compareBy({it.title}))
            notifyDataSetChanged()
        }
        fun sortTitle2(){
            movies = movies.sortedWith(compareByDescending({it.title}))
            notifyDataSetChanged()
        }

        //




        override fun getItemCount(): Int {

            return movies.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {


            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return MovieViewHolder(v)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {


            //holder.bindItems(movieList[position])

            Glide.with(this@ListFragment).load(resources.getString(R.string.picture_base_url)+movies[position].poster_path).apply( RequestOptions().override(128, 128)).into(holder.view.findViewById(R.id.poster))

            holder.view.findViewById<TextView>(R.id.title).text=movies[position].title

            val ten = 10;

            holder.view.findViewById<TextView>(R.id.rating).text= "IMDB Rating: " + movies[position].vote_average.toString() + " / " + ten.toString()


            holder.itemView.setOnClickListener(){


                // interact with the item
                val cal: Calendar = Calendar.getInstance()

                cal.time = movies[position].release_date
                val year = cal.get(Calendar.YEAR)

                holder.view.findNavController().navigate(R.id.action_listFragment_to_detailFragment,
                    bundleOf("poster_path" to movies[position].poster_path,
                        "title" to movies[position].title,
                        "overview" to movies[position].overview,
                        "vote_average" to movies[position].vote_average,
                        "vote_count" to movies[position].vote_count,
                        "release_date" to year,
                        "id" to movies[position].id,
                        "liked" to movies[position].liked)
                )

            }

        }



        inner class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

                if (view != null) {


                }


            }




        }
    }




}
