package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/*
viewmodel survive config changes while activities fragments or view do not
viewmodel
 */

class GameViewModel :ViewModel(){

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }


    private val timer:CountDownTimer




    init {
        Log.i("GameViewModel","GameViweModel created")
        resetList()
        nextWord()
        _eventGameFinish.value=false
        _score.value=0   // would get an error if we do not set ans the app will crash


        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                _currentTime.value=(millisUntilFinished/ ONE_SECOND)
            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _currentTime.value= DONE
                _eventGameFinish.value=true

            }
        }
        timer.start()
    }



        override fun onCleared() {
            super.onCleared()
            timer.cancel()
            Log.i("GameViewModel","GameViewModel desroyed")


        }
}
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
    get() = _currentTime

    //  THE FOLLOWING IS THE CODE MOVED FROM THE GAMEFRAGMENT
     private var _word = MutableLiveData<String>()
     val word:LiveData<String>
     get() = _word

    // The current score
     private var _score = MutableLiveData<Int>()
     val score: LiveData<Int>
         get()= _score



    private var _eventGameFinish=MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
        get() = _eventGameFinish




// The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>


 fun resetList() {
    wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
    )
    wordList.shuffle()
}



    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
        //    gameFinished()
            resetList()
        }
            _word.value= wordList.removeAt(0)


    }

     fun onSkip() {
        _score.value=_score.value?.minus(1)
        nextWord()
    }

     fun onCorrect() {
        _score.value=_score.value?.plus(1)
        nextWord()
    }

    fun onGameFinished()
    {
        _eventGameFinish.value=false
    }



