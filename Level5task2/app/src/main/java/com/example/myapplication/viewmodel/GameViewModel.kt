package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.Game
import com.example.myapplication.database.GameRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepo(application.applicationContext)

    val games: LiveData<MutableList<Game>> = gameRepository.games

    fun remove(id : Long){
        GlobalScope.launch {
            gameRepository.remove(id)
        }
    }

    fun deleteAll(){
        GlobalScope.launch {
            gameRepository.removeAll()
        }
    }

    fun insert(game: Game) {
        GlobalScope.launch {
            gameRepository.insert(game)
        }
    }

}
