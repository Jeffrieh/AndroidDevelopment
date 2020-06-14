package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM Game")
    fun getGames(): LiveData<MutableList<Game>>

    @Update
    suspend fun updateGame(game: Game)

    @Query("DELETE FROM GAME")
    fun removeAll()

    @Query("DELETE FROM GAME WHERE id = :id")
    fun remove(id: Long)
}
