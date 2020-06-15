package com.example.watchrr.ui.discovery

import android.content.Context
import android.graphics.Point
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.watchrr.R
import com.example.watchrr.data.movie.MovieRepository
import com.example.watchrr.data.user.Movie
import com.example.watchrr.data.user.UserRepository
import com.example.watchrr.helpers.Utils
import com.mindorks.placeholderview.SwipeDirection
import com.mindorks.placeholderview.annotations.*
import com.mindorks.placeholderview.annotations.swipe.*
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.sqrt

@Layout(R.layout.cardview_layout)
class MovieCard(
    val context: Context,
    val movie: Movie,
    val cardViewHolderSize: Point
) {

    @View(R.id.profileImageView)
    lateinit var profileImageView: ImageView

    @View(R.id.nameAgeTxt)
    lateinit var nameAgeTxt: TextView

    @View(R.id.locationNameTxt)
    lateinit var locationNameTxt: TextView

    @SwipeView
    lateinit var swipeView: android.view.View

    var movieRepository = MovieRepository()

    @JvmField
    @Position
    var position: Int = 0;

    @Resolve
    fun onResolved() {
        Glide.with(context).load(movie.image_url).bitmapTransform(
            RoundedCornersTransformation(
                context,
                Utils.dpToPx(7),
                0,
                RoundedCornersTransformation.CornerType.TOP
            )
        )
            .into(profileImageView)
        nameAgeTxt.text = "${movie.title},  ${movie.release_date}"
        swipeView.alpha = 1f
    }


    @Click(R.id.profileImageView)
    fun onClick() {
        Log.d("EVENT", "profileImageView click")
    }


    @SwipeCancelState
    fun onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState")
        swipeView.alpha = 1f
    }

    @SwipeOut
    fun onSwipeOut(){
//        userRepository.likeMovie(movie)
//        callback.DontLike(movie)
    }

    @SwipeIn
    fun onSwipeIn(){
        //like movie
        movieRepository.likeMovie(movie)
    }

    @SwipeTouch
    public fun onSwipeTouch(xStart: Float, yStart: Float, xCurrent: Float, yCurrent: Float) {

        val cardHolderDiagonalLength =
            sqrt(
                Math.pow(cardViewHolderSize.x.toDouble(), 2.0)
                        + (Math.pow(cardViewHolderSize.y.toDouble(), 2.0))
            )
        val distance = sqrt(
            Math.pow(xCurrent.toDouble() - xStart.toDouble(), 2.0)
                    + (Math.pow(yCurrent.toDouble() - yStart, 2.0))
        )

        val alpha = 1 - distance / cardHolderDiagonalLength

        Log.d(
            "DEBUG", "onSwipeTouch "
                    + " xStart : " + xStart
                    + " yStart : " + yStart
                    + " xCurrent : " + xCurrent
                    + " yCurrent : " + yCurrent
                    + " distance : " + distance
                    + " TotalLength : " + cardHolderDiagonalLength
                    + " alpha : " + alpha
        )

        swipeView.alpha = alpha.toFloat();
    }

    interface Callback {
        fun Like(movie : Movie)
        fun DontLike(movie : Movie)
        fun revert()
    }
}