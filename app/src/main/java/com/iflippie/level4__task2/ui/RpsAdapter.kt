package com.iflippie.level4__task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iflippie.level4__task2.R
import com.iflippie.level4__task2.model.RpsGame
import kotlinx.android.synthetic.main.item_game.view.*

class RpsAdapter(private val rpsGames: List<RpsGame>) : RecyclerView.Adapter<RpsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int = rpsGames.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(rpsGames[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rpsGame: RpsGame) {
            itemView.tvDate.text = rpsGame.dateText.toString()
            itemView.tvResult.text = rpsGame.resultText
            itemView.ivComputer.setImageResource(RpsGame.RPS_DRAWABLES[rpsGame.computerIndex])
            itemView.ivPlayer.setImageResource(RpsGame.RPS_DRAWABLES[rpsGame.playerIndex])
        }
    }
}