package com.iflippie.level4__task2.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iflippie.level4__task2.R
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "gameTable")
data class RpsGame(

    @ColumnInfo(name = "gameDate")
    var dateText: Date,

    @ColumnInfo(name = "gamePlayer")
    var playerIndex: Int,

    @ColumnInfo(name = "gameComputer")
    var computerIndex: Int,

    @ColumnInfo(name = "gameResult")
    var resultText: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable {

    companion object {
        val RPS_DRAWABLES = arrayOf(
            R.drawable.rock,
            R.drawable.paper,
            R.drawable.scissors
        )
    }
}