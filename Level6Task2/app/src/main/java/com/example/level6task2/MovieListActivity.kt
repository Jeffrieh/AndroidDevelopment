package com.example.level6task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.level6task2.data.Movie
import com.example.level6task2.data.Movies
import kotlinx.android.synthetic.main.movie_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [MovieDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MovieListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val navController = findNavController(R.id.nav_host_fragment)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button event and return true to override
        // the default behavior the same way as the OnBackPressedCallback.
        println("navigate up")



        // If no custom behavior was handled perform the default action.
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.movieListFragment)
        return super.onSupportNavigateUp()
    }

}