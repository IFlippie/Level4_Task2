package com.iflippie.level4__task2.database

import android.content.Context
import com.iflippie.level4__task2.model.RpsGame

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
