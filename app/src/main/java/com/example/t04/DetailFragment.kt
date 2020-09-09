package com.example.t04

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {




    var poster_path: String? = null
    var title: String? = null
    var overview: String? = null
    var vote_average: Float? = null
    var vote_count: Long? = null
    var year: Int? = null
    var likeButton: Button? = null
    var unlikeButton: Button? = null
    var liked: Boolean? = null
    var id: Long? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        poster_path = this.arguments?.getString("poster_path")
        title = this.arguments?.getString("title")
        overview = this.arguments?.getString("overview")
        vote_average = this.arguments?.getFloat("vote_average")
        vote_count = this.arguments?.getLong("vote_count")
        year = this.arguments?.getInt("release_date")
        liked = this.arguments?.getBoolean("liked")
        id = this.arguments?.getLong("id")


        Glide.with(this@DetailFragment).load(resources.getString(R.string.picture_base_url)+poster_path).apply( RequestOptions().override(128, 128)).into(view.findViewById(R.id.poster_detail))


        val model = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        view.findViewById<TextView>(R.id.title_detail).text = title
        view.findViewById<TextView>(R.id.overview_title).text = overview
        //view.findViewById<TextView>(R.id.).text = "Votes : " + vote_count.toString()
        view.findViewById<TextView>(R.id.year_title).text = "Release : " + year.toString()
        //view.findViewById<TextView>(R.id.rating_detail).text = "Rating : " + vote_average.toString()


        likeButton = view.findViewById(R.id.like_button)
        unlikeButton = view.findViewById(R.id.unlike)



        likeButton?.setOnClickListener {

            //if (liked!!) {
                //model.showLiked(id!!, false)
                //likeButton?.text = "Like"

            //} else {

                model.showLiked(id!!, true)
                likeButton?.isClickable = false;
                likeButton?.isEnabled = false;
            //}
            //liked = !liked!!

            }
        unlikeButton?.setOnClickListener {
            if (liked!!){
                model.showLiked(id!!, false)
                likeButton?.text = "Like"
                likeButton?.isClickable = true;
                likeButton?.isEnabled = true;
            }
        }
        }
    }


