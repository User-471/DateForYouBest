package com.dateforyou.best.ui.splash.choose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.dateforyou.best.R
import com.dateforyou.best.models.Card
import com.dateforyou.best.ui.splash.choose.adapter.KolodaAdapter
import com.dateforyou.best.ui.splash.gender.GenderActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yalantis.library.KolodaListener
import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class ChooseActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChooseActivity::class.java)
        }
    }

    private var names = arrayListOf<String>()
    private var photos = arrayListOf<String>()
    private var adapter: KolodaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        initData()
        initKoloda()
        initializeDeck()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        fab_cancel?.setOnClickListener { koloda?.onClickLeft() }
        fab_select?.setOnClickListener { koloda?.onClickRight() }
    }

    private fun initData() {
        val namesJsonFileString = getJsonDataFromAsset(applicationContext, "names.json")
        val nameType = object : TypeToken<ArrayList<String>>() {}.type

        names = Gson().fromJson(namesJsonFileString, nameType)

        val photosJsonFileString = getJsonDataFromAsset(applicationContext, "photos.json")
        val photosType = object : TypeToken<ArrayList<String>>() {}.type

        photos = Gson().fromJson(photosJsonFileString, photosType)
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun initKoloda() {
        adapter = KolodaAdapter(this, fillData().toList())
        koloda.adapter = adapter
    }

    private fun fillData(): ArrayList<Card> {
        val cards = arrayListOf<Card>()

        val namesList = arrayListOf<String>()
        val photosList = arrayListOf<String>()

        namesList.addAll(names.shuffled().take(5))
        photosList.addAll(photos.shuffled().take(5))

        for(i in 0..4) {
            cards.add(
                Card(
                    i,
                    namesList[i],
                    photosList[i]
                )
            )
        }

        return cards
    }

    private fun initializeDeck() {
        koloda.kolodaListener = object : KolodaListener {

            override fun onCardSwipedLeft(position: Int) {
//                parseAnswerToLeft(position + 1)
                when (koloda.size) {
                    3 -> {
                        koloda[2].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[2].invalidate()
                    }
                    2 -> {
                        koloda[1].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[1].invalidate()
                    }
                    1 -> {
                        koloda[0].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[0].invalidate()
                    }
                    else -> {
                        koloda[3].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[3].invalidate()
                    }
                }
            }

            override fun onCardSwipedRight(position: Int) {
//                parseAnswerToRight(position + 1)
                when (koloda.size) {
                    3 -> {
                        koloda[2].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[2].invalidate()
                    }
                    2 -> {
                        koloda[1].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[1].invalidate()
                    }
                    1 -> {
                        koloda[0].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[0].invalidate()
                    }
                    else -> {
                        koloda[3].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[3].invalidate()
                    }
                }
            }

            override fun onClickLeft(position: Int) {
//                parseAnswerToLeft(position + 1)
                super.onClickLeft(position)

                when (koloda.size) {
                    3 -> {
                        koloda[2].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[2].invalidate()
                    }
                    2 -> {
                        koloda[1].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[1].invalidate()
                    }
                    1 -> {
                        koloda[0].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[0].invalidate()
                    }
                    else -> {
                        koloda[3].findViewById<ConstraintLayout>(R.id.cl_wrong).visibility =
                            View.VISIBLE
                        koloda[3].invalidate()
                    }
                }
            }

            override fun onClickRight(position: Int) {
//                parseAnswerToRight(position + 1)
                super.onClickRight(position)
                when (koloda.size) {
                    3 -> {
                        koloda[2].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[2].invalidate()
                    }
                    2 -> {
                        koloda[1].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[1].invalidate()
                    }
                    1 -> {
                        koloda[0].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[0].invalidate()
                    }
                    else -> {
                        koloda[3].findViewById<ConstraintLayout>(R.id.cl_right).visibility =
                            View.VISIBLE
                        koloda[3].invalidate()
                    }
                }
            }

            override fun onCardDrag(position: Int, cardView: View, progress: Float) {
                super.onCardDrag(position, cardView, progress)

                if (progress < 0) {
                    cardView.findViewById<ConstraintLayout>(R.id.cl_wrong).visibility = View.VISIBLE
                    cardView.findViewById<ConstraintLayout>(R.id.cl_right).visibility = View.GONE
                }
                if (progress > 0) {
                    cardView.findViewById<ConstraintLayout>(R.id.cl_wrong).visibility = View.GONE
                    cardView.findViewById<ConstraintLayout>(R.id.cl_right).visibility = View.VISIBLE
                }
                if (progress > -0.1 && progress < 0.1) {
                    cardView.findViewById<ConstraintLayout>(R.id.cl_wrong).visibility = View.GONE
                    cardView.findViewById<ConstraintLayout>(R.id.cl_right).visibility = View.GONE
                }
            }

            override fun onEmptyDeck() {
                lifecycleScope.launch {
                    delay(250)
                    finish()
                    startActivity(newIntent(this@ChooseActivity))
                }
            }
        }
    }
}