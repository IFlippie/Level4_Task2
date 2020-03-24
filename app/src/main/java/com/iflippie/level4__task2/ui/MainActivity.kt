package com.iflippie.level4__task2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iflippie.level4__task2.R
import com.iflippie.level4__task2.database.GameRepository
import com.iflippie.level4__task2.model.RpsGame

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private val rpsGameList = arrayListOf<RpsGame>()
    private val rpsAdapter =
        RpsAdapter(rpsGameList)
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_RPS)

        gameRepository = GameRepository(this)
        initViews()
        //fab.setOnClickListener { addProduct()}
        ibRock.setOnClickListener {buttonRPS(0)}
        ibPaper.setOnClickListener {buttonRPS(1)}
        ibScissors.setOnClickListener {buttonRPS(2)}
    }

    private fun initViews()
    {
        getGameListFromDatabase()
        rvGameList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvGameList.adapter = rpsAdapter
        rvGameList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvGameList)

    }

    private fun getGameListFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@MainActivity.rpsGameList.clear()
            this@MainActivity.rpsGameList.addAll(gameList)
            this@MainActivity.rpsAdapter.notifyDataSetChanged()
        }
    }

    private fun addGame(playersChoice: Int, computersChoice: Int, result: String) {
            mainScope.launch {
                val rpsgame = RpsGame(
                    Calendar.getInstance().time,
                    playersChoice,
                    computersChoice,
                    result
                )

                withContext(Dispatchers.IO) {
                    gameRepository.insertGame(rpsgame)
                }

                getGameListFromDatabase()
            }
        }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val productToDelete = rpsGameList[position]
                mainScope.launch {
                    withContext(Dispatchers.IO) {
                        gameRepository.deleteGame(productToDelete)
                    }
                    getGameListFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
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
        return when (item.itemId) {
            R.id.action_delete_shopping_list -> {
                deleteRpsGamesList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    private fun deleteRpsGamesList() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGameListFromDatabase()
        }
    }

    private fun buttonRPS(rps: Int)
    {
        val playersChoice = rps
        val computersChoice = computerRPS()
        val results = checkResults(playersChoice, computersChoice)
        addGame(playersChoice, computersChoice, results)
    }
    private fun computerRPS(): Int
    {
        return (0..2).random()
    }

    private fun checkResults(playersChoice: Int, computersChoice: Int): String
    {
        if (playersChoice == computersChoice)
        {
            return getString(R.string.result_Tie)
        }
        else if ((playersChoice == 0 && computersChoice == 2) ||
                (playersChoice == 1 && computersChoice == 0) ||
                (playersChoice == 2 && computersChoice == 1))
        {
            return getString(R.string.result_Winner)
        }
        else if ((playersChoice == 0 && computersChoice == 1) ||
                (playersChoice == 1 && computersChoice == 2) ||
                (playersChoice == 2 && computersChoice == 0))
        {
            return getString(R.string.result_Loser)
        }
        else return getString(R.string.result_Nothing)
    }

}
