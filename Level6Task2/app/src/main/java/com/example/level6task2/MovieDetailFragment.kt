package com.example.level6task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.level6task2.data.Movie
import kotlinx.android.synthetic.main.movie_detail_fragment.view.*

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MovieListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false)

        val homeViewModel = ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)

        println(homeViewModel.selectedMovie.value?.title)

        homeViewModel.selectedMovie.observe(viewLifecycleOwner, Observer {
            rootView.title.text = it.title
            rootView.description.text = it.overview
        });


        return rootView
    }

}