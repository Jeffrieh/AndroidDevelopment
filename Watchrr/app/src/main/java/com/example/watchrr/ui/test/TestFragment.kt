package com.example.watchrr.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.watchrr.R
import com.example.watchrr.data.group.GroupRepository
import com.example.watchrr.data.movie.MovieRepository
import com.example.watchrr.data.user.Movie
import com.example.watchrr.data.user.User
import com.example.watchrr.data.user.UserRepository
import com.example.watchrr.ui.discovery.HomeViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.view.*
import kotlinx.android.synthetic.main.fragment_test.view.btnResetLikes
import org.json.JSONObject
import java.util.*


class TestFragment : Fragment() {

    val userRepository = UserRepository()
    val groupRepository = GroupRepository()
    val movieRepository = MovieRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_test, container, false)
        val homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)

        root.btnNewUser.setOnClickListener {
            homeViewModel.createUser(
                User(UUID.randomUUID().toString(), "testUser", "testUser@info.nl")
            )
        }

        root.btnRemoveGroups.setOnClickListener {
        }

        root.btnAddRandomMovie.setOnClickListener {
            val mid = (1..4500).random()
            val queue = Volley.newRequestQueue(root.context)
            val url =
                "https://api.themoviedb.org/3/movie/${mid}?api_key=72b7ea0485ed639ecc86f8038cc9bada&language=en-US"

// Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    val jsonObj = JSONObject(response)


                    if (!jsonObj.isNull("backdrop_path")) {
                        println("adding movie")
                        var title = jsonObj.get("original_title") as String;
                        var tagline = jsonObj.get("tagline") as String;
                        var year = jsonObj.get("release_date") as String;
                        var image_url =
                            "http://image.tmdb.org/t/p/w500${jsonObj.get("backdrop_path")}" as String
                        movieRepository.addMovie(Movie(title, tagline, image_url, year))
                    }
                },
                Response.ErrorListener { })
            queue.add(stringRequest)
        }

        root.btnResetLikes.setOnClickListener{
            userRepository.resetLikes()
        }

//        root.btnLikeMovie.setOnClickListener {
//            GlobalScope.launch {
//                homeViewModel.likeMovie(
//                    "1558e2ae-ff00-40a9-af4e-470986af0c73",
//                    homeViewModel.getMovieByTitle("Spiderman")
//                )
//                homeViewModel.likeMovie(
//                    "IZQCZwb3YWzlpYTOUkpV",
//                    homeViewModel.getMovieByTitle("Spiderman")
//                )
//            }
//        }

//        homeViewModel.user.observe(viewLifecycleOwner, Observer {
//            println("in test fragment observing!")
//            currentUser = it;
//        })


        return root
    }
}