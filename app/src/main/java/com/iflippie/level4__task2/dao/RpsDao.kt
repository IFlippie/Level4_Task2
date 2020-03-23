package com.iflippie.level4__task2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.iflippie.level4__task2.RpsGame

@Dao
interface RpsDao {

    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames(): List<RpsGame>

    @Insert
    suspend fun insertGame(rpsGame: RpsGame)

    @Delete
    suspend fun deleteGame(rpsGame: RpsGame)

    @Query("DELETE FROM gameTable")
    suspend fun deleteAllGames()

}
