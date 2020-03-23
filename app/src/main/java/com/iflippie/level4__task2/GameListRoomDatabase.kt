package com.iflippie.level4__task2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iflippie.level4__task2.dao.RpsDao

@Database(entities = [RpsGame::class], version = 1, exportSchema = false)
abstract class GameListRoomDatabase : RoomDatabase() {

    abstract fun productDao(): RpsDao

    companion object {
        private const val DATABASE_NAME = "RPSGAMES_LIST_DATABASE"

        @Volatile
        private var gameListRoomDatabaseInstance: GameListRoomDatabase? = null

        fun getDatabase(context: Context): GameListRoomDatabase? {
            if (gameListRoomDatabaseInstance == null) {
                synchronized(GameListRoomDatabase::class.java) {
                    if (gameListRoomDatabaseInstance == null) {
                        gameListRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,GameListRoomDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return gameListRoomDatabaseInstance
        }
    }

}
