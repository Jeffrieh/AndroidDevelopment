package com.example.watchrr.ui.discovery

import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders.of
import com.example.watchrr.R
import com.example.watchrr.data.movie.MovieRepository
import com.example.watchrr.helpers.Utils
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipeDirectionalView
import com.mindorks.placeholderview.SwipePlaceHolderView
import kotlinx.android.synthetic.main.app_bar_main.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val homeViewModel = of(requireActivity()).get(HomeViewModel::class.java)

        return (when(item.itemId) {
            R.id.action_wipe-> {
                homeViewModel.wipeLikes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = of(requireActivity()).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val swipeView: SwipeDirectionalView = root.findViewById(R.id.swipeView);

        val bottomMargin = Utils.dpToPx(150)

        val windowSize = Utils.getDisplaySize(requireActivity().windowManager)
        swipeView!!.builder
            .setDisplayViewCount(3)
            .setIsUndoEnabled(true)
            .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
            .setSwipeVerticalThreshold(Utils.dpToPx(50))
            .setSwipeHorizontalThreshold(Utils.dpToPx(50))
            .setHeightSwipeDistFactor(10f)
            .setWidthSwipeDistFactor(5f)
            .setSwipeDecor(
                SwipeDecor()
                    .setViewWidth(windowSize.x)
                    .setViewHeight(windowSize.y - bottomMargin)
                    .setViewGravity(Gravity.TOP)
                    .setPaddingTop(20)
                    .setSwipeAnimTime(3)
                    .setRelativeScale(0.01f)
            )


        val cardViewHolderSize = Point(windowSize.x, windowSize.y - bottomMargin)

        println("getting movies")
        println(homeViewModel.movies)

        homeViewModel.movies.observe(viewLifecycleOwner, Observer {
            it.forEach {
                swipeView!!.addView(
                    MovieCard(
                        requireContext(),
                        it,
                        cardViewHolderSize
                    )
                )
            }
        })
//
//        homeViewModel.user.observe(viewLifecycleOwner, Observer {
//            println("Logged in with : " + it?.email)
////            homeViewModel.setUser()
//        })

        homeViewModel.user.observe(viewLifecycleOwner, Observer {
            println("something in user changed! $it")
        })

        val undobtn: ImageButton = root.findViewById(R.id.undoBtn)
        undobtn.setOnClickListener({ swipeView!!.undoLastSwipe() })

        val acceptbtn: ImageButton = root.findViewById(R.id.acceptBtn)
        acceptbtn.setOnClickListener({ swipeView!!.doSwipe(true) })

        val rejectbtn: ImageButton = root.findViewById(R.id.rejectBtn)
        rejectbtn.setOnClickListener({ swipeView!!.doSwipe(false) })

        setHasOptionsMenu(true)
//        swipeView!!.addItemRemoveListener {
//            if (isToUndo) {
//                isToUndo = false
//                swipeView!!.undoLastSwipe()
//            }

        //      }


        return root
    }

}