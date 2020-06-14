package com.example.myapplication.database


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.Game

class GameRepo(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameDatabase.getDatabase(context)
        gameDao = database!!.noteDao()
    }

    val games: LiveData<MutableList<Game>> = gameDao.getGames()
//
//    suspend fun get(game : Game){
//        gameDao.getGame(game)
//    }

    suspend fun update(game: Game) {
        gameDao.updateGame(game)
    }

    suspend fun remove(id: Long) {
        gameDao.remove(id)
    }

    suspend fun removeAll() {
        gameDao.removeAll()
    }

    suspend fun insert(game: Game) {
        gameDao.insertGame(game)
    }
}
