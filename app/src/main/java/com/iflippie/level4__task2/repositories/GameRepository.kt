package com.iflippie.level4__task2.repositories

import android.content.Context
import com.iflippie.level4__task2.RpsGame
import com.iflippie.level4__task2.GameListRoomDatabase
import com.iflippie.level4__task2.dao.RpsDao

class GameRepository(context: Context) {

    private val rpsDao: RpsDao

    init {
        val database = GameListRoomDatabase.getDatabase(context)
        rpsDao = database!!.productDao()
    }

    suspend fun getAllGames(): List<RpsGame> = rpsDao.getAllGames()

    suspend fun insertGame(rpsGame: RpsGame) = rpsDao.insertGame(rpsGame)

    suspend fun deleteGame(rpsGame: RpsGame) = rpsDao.deleteGame(rpsGame)

    suspend fun deleteAllGames() = rpsDao.deleteAllGames()

}
