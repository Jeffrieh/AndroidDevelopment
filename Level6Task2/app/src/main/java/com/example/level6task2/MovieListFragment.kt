package com.example.level6task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level6task2.data.Movie
import com.example.level6task2.data.Movies
import kotlinx.android.synthetic.main.movie_list_fragment.*
import kotlinx.android.synthetic.main.movie_list_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MovieListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieListFragment : Fragment() {

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
        val rootView = inflater.inflate(R.layout.movie_list_fragment, container, false)

        val model = ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)

        val onClickListener: View.OnClickListener

        onClickListener = View.OnClickListener { v ->
            model.selectedMovie.value = v.tag as Movie
            findNavController().navigate(R.id.movieDetailFragment)
        }

        var recyclerView: RecyclerView = rootView.findViewById(R.id.rvMovies)

        rootView.button.setOnClickListener {
            GlobalScope.launch {
                println("launching coroutine")
                model.getMovies(etYear.text.toString().toInt())
            }
        }

        recyclerView.layoutManager = GridLayoutManager(rootView.context, 2)

        model.movies.observe(viewLifecycleOwner, Observer {
            println("movies changed!")
            var results = it.body() as Movies
            recyclerView.adapter =
                SimpleItemRecyclerViewAdapter(
                    rootView.context,
                    results.results,
                    onClickListener
                )
        });

        return rootView
    }


}