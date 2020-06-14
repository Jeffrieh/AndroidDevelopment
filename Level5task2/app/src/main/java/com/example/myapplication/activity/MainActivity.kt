package com.example.myapplication.activity


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.GameAdapter
import com.example.myapplication.database.Game
import com.example.myapplication.viewmodel.GameViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var gameAdapter: GameAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setTitle("Game Backlog")
        setSupportActionBar(toolbar)



        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvGames)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameAdapter = GameAdapter(mutableListOf(), applicationContext);
        recyclerView.adapter = gameAdapter

        var itemTouchHelper: ItemTouchHelper? = null

        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    var clickedUndo = false;
                    if (direction == 4) {
                        val game: Game = gameAdapter.getGameAt(viewHolder.adapterPosition)
                        gameAdapter.softDelete(game)


                        var snackbar = Snackbar.make(layout, "Games Deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo") { _ ->
                                clickedUndo = true;
                                gameAdapter.add(game)
                            }

                        snackbar.addCallback(object : Snackbar.Callback() {
                            override fun onDismissed(snackbar: Snackbar, event: Int) {
                                if (!clickedUndo) {
                                    gameViewModel.remove(game.id!!)
                                }
                            }
                        })
                        snackbar.show()
                    }
                }
            }

        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        gameViewModel.games.observe(this, Observer {
            gameAdapter.setGames(it)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        var view: CoordinatorLayout = findViewById(R.id.layout);

        return when (item.itemId) {
            R.id.action_delete -> {
                var clickedUndo = false;
                gameAdapter.softDelete();

                var snackbar = Snackbar.make(view, "Games Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { _ ->
                        clickedUndo = true;
                        gameAdapter.revert();
                    }

                snackbar.addCallback(object : Snackbar.Callback() {
                    override fun onShown(snackbar: Snackbar) {
                        println("show snack!")
                    }

                    override fun onDismissed(snackbar: Snackbar, event: Int) {
                        if (!clickedUndo) {
                            gameViewModel.deleteAll()
                        }
                    }
                })
                snackbar.show()
                false
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
