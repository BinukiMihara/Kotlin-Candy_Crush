package com.example.candycrush

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.candycrush.util.OnSwipeListener
import java.util.Arrays.asList


class MainActivity : AppCompatActivity() {
    /** add candies**/
    var candies = intArrayOf(       // Define an array of resource IDs for the candy images
        R.drawable.bluecandy,
        R.drawable.greencandy,
        R.drawable.orangecandy,
        R.drawable.purplecandy,
        R.drawable.redcandy,
        R.drawable.yellowcandy
    )
    // Variables to store game board dimensions
    var widthOfBlock :Int = 0
    var noOfBlock: Int = 8
    var widthOfScreen: Int = 0

    lateinit var candy: ArrayList<ImageView>      // An ArrayList to store references to all ImageView objects on the board
    // Track IDs of candies being dragged and replaced
    var candyToBeDragged : Int = 0
    var candyToBeReplaced: Int = 0

    var notCandy: Int = R.drawable.transparent        // Resource ID for the transparent image (used for empty spaces)

    lateinit var mHandler: Handler      // A Handler object used for scheduling repetitive tasks
    // TextViews to display score and player name
    private lateinit var scoreResult:TextView
    private lateinit var name:TextView

    var score = 0   // Current game score
    var interval = 100L  // Interval for checking for matches (in milliseconds)

    lateinit var sharedPreferences: SharedPreferences    // SharedPreferences for storing player name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize score and player name TextViews
        scoreResult =  findViewById(R.id.score)
        name = findViewById(R.id.playerName)

        // Retrieve player name from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val playerName = sharedPreferences.getString("UserName", "")
        name.text = playerName

        // Get screen dimensions
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        widthOfScreen = displayMetrics.widthPixels

        var heighOfScreen = displayMetrics.heightPixels
        widthOfBlock = widthOfScreen/noOfBlock

        candy = ArrayList()         // Initialize the candy ArrayList
        createBoard()


        for (imageView in candy) {
            imageView.setOnTouchListener(
                object : OnSwipeListener(this) {
                    override fun onSwipeRight() {
                        super.onSwipeRight()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged + 1
                        candyInterChange()
                    }

                    override fun onSwipeLeft() {
                        super.onSwipeLeft()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged - 1
                        candyInterChange()
                    }

                    override fun onSwipeTop() {
                        super.onSwipeTop()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged - noOfBlock
                        candyInterChange()
                    }

                    override fun onSwipeBottom() {
                        super.onSwipeBottom()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged + noOfBlock
                        candyInterChange()
                    }
                }
            )
        }

        mHandler = Handler()   // Initialize the Handler
        startRepeat()      // Call method to start checking for matches repeatedly

    }


    private fun candyInterChange() {         // This function swaps the images and tags of two candy ImageView objects based on their IDs (candyToBeDragged and candyToBeReplaced)
        if (candyToBeDragged >= 0 && candyToBeReplaced >= 0 &&
            candyToBeDragged < candy.size && candyToBeReplaced < candy.size
        ) {
            val background: Int = candy[candyToBeReplaced].tag as Int
            val background1: Int = candy[candyToBeDragged].tag as Int

            candy[candyToBeDragged].setImageResource(background)
            candy[candyToBeReplaced].setImageResource(background1)

            candy[candyToBeDragged].tag = background
            candy[candyToBeReplaced].tag = background1
        }
    }

    private fun checkRowForThree(){
        for(i in 0..61){
            var chosedCandy = candy.get(i).tag
            var isBlank: Boolean= candy.get(i).tag == notCandy
            val notValid = arrayOf(6,7,14,15,22,23,30,31,38,39,46,47,54,55)  // List of invalid positions to avoid out-of-bounds errors
            val list = asList(*notValid) // Convert array to list for contains check
            if(!list.contains(i)){   // Convert array to list for contains check
                var x= i  // Index variable for checking adjacent candies
                if (candy.get(x++).tag as Int==chosedCandy // Check next candy's type
                    && !isBlank // Check if next candy is not empty
                    &&candy.get(x++).tag as Int == chosedCandy // Check second next candy's type
                    && candy.get(x).tag as Int ==chosedCandy)     // Check third next candy's type
                {
                    score = score +3
                    scoreResult.text = "$score"      // Update score text view
                    candy.get(x).setImageResource(notCandy) // Set image of third next candy to empty
                    candy.get(x).setTag(notCandy)    // Set tag of third next candy to empty
                    x--
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                    x--
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)

                }

            }
        }
        moveDownCandies()
    }
    private fun checkColumnForThree(){
        for(i in 0..47){
            // Loop iterates through each candy in a column (from index 0 to 47, which represents the last valid index in the first four columns)
            var chosedCandy = candy.get(i).tag     // Get the tag (candy type) of the current candy
            var isBlank: Boolean= candy.get(i).tag == notCandy    // Check if the current candy is empty

            var x= i   // Initialize an index variable 'x' to keep track of the current candy position

            if (candy.get(x).tag as Int==chosedCandy // Check current candy type
                && !isBlank
                &&candy.get(x+noOfBlock).tag as Int == chosedCandy      // Check candy below type
                && candy.get(x+2*noOfBlock).tag as Int ==chosedCandy)    // Check candy two below type
            {
                score = score +3
                scoreResult.text = "$score"

                candy.get(x).setImageResource(notCandy)
                candy.get(x).setTag(notCandy)
                x = x + noOfBlock   // Move index 'x' down one block

                candy.get(x).setImageResource(notCandy)
                candy.get(x).setTag(notCandy)
                x = x + noOfBlock

                candy.get(x).setImageResource(notCandy)
                candy.get(x).setTag(notCandy)

            }


        }
        moveDownCandies()
    }
    private fun moveDownCandies() {
        val firstRow = arrayOf(1,2,3,4,5,6,7)  // Array containing indexes of the first row (used for special handling)
        val list = asList(*firstRow)           // Convert array to list for contains check
        // Loop iterates from the bottom row (index 55) to the top row (index 0)
        for(i in 55 downTo 0){
            // Check if the candy one block below the current candy (i + noOfBlock) is empty
            if(candy.get(i + noOfBlock).tag as Int == notCandy){

                // If it's empty, copy the image and tag (candy type) of the candy above it (i)
                candy.get(i + noOfBlock).setImageResource(candy.get(i).tag as Int)
                candy.get(i + noOfBlock).setTag(candy.get(i).tag as Int)
                // Set the image and tag of the candy above it (i) to empty
                candy.get(i).setImageResource(notCandy)
                candy.get(i).setTag(notCandy)

                // Check if the current candy is in the first row (using the list converted from the array) and if it's empty
                if(list.contains(i) && candy.get(i).tag == notCandy){
                    var randomColor :Int = Math.abs(Math.random()* candies.size).toInt()        // If it's empty in the first row, generate a random color index
                    candy.get(i).setImageResource(candies[randomColor])    // Set the image of the candy to the random candy color from the candies array
                    candy.get(i).setTag(candies[randomColor])      // Set the tag (candy type) of the candy to the random candy color index

                }
            }
        }
        for(i in 0..7){
            if(candy.get(i).tag as Int == notCandy){     // Check if the current candy in the first row is empty

                var randomColor :Int = Math.abs(Math.random()* candies.size).toInt()        // If it's empty, generate a random color index
                candy.get(i).setImageResource(candies[randomColor])
                candy.get(i).setTag(candies[randomColor])

            }
        }
    }
    val repeatChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                checkRowForThree()
                checkColumnForThree()
                moveDownCandies()
            } finally {
                mHandler.postDelayed(this, interval)    // Schedule this Runnable to run again after the interval
            }
        }
    }
    private fun startRepeat() {
        // Starts the repetitive execution of the repeatChecker object.
        repeatChecker.run()
    }
    private fun createBoard() {
        val gridLayout = findViewById<GridLayout>(R.id.board)
        // Set the number of rows and columns in the GridLayout based on noOfBlock
        gridLayout.rowCount = noOfBlock
        gridLayout.columnCount = noOfBlock

        // Set the width and height of the GridLayout to match the screen size
        gridLayout.layoutParams.width = widthOfScreen
        gridLayout.layoutParams.height = widthOfScreen

        // Loop through each cell in the board (noOfBlock * noOfBlock iterations)
        for(i in 0 until noOfBlock * noOfBlock){
            val imageView = ImageView(this)     // Create a new ImageView object
            imageView.id = i      // Set the ID of the ImageView to the current loop index (i)
            imageView.layoutParams = android.view.ViewGroup.LayoutParams(widthOfBlock,widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock

            var random:Int = Math.floor(Math.random()* candies.size).toInt()         // Generate a random index within the range of the candies array

            //randomIndex from Candies array
            imageView.setImageResource(candies[random])
            imageView.setTag(candies[random])

            candy.add(imageView)
            gridLayout.addView(imageView)
            }

        }

}
