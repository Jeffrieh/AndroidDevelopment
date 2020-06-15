package com.example.watchrr.ui.groups.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchrr.R
import com.example.watchrr.data.user.Movie
import com.example.watchrr.ui.discovery.HomeViewModel
import com.example.watchrr.ui.groups.GroupsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroupsDetailFragment : Fragment() {

    private lateinit var friendsViewModel: GroupsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_groups_detail, container, false)
        val homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        val groupsViewModel =
            ViewModelProviders.of(requireActivity()).get(GroupsViewModel::class.java)

        val recyclerViewMatches: RecyclerView = root.findViewById(R.id.rvMatches)
        recyclerViewMatches.layoutManager = LinearLayoutManager(root.context)

        groupsViewModel.currentGroup.observe(viewLifecycleOwner, Observer {
            println(it)
        })

        groupsViewModel.matchesInGroup.observe(viewLifecycleOwner, Observer {
            if(!it.isEmpty()){
                recyclerViewMatches.adapter = GroupsDetailAdapter(it, requireContext())
            }
        })

//        groupsViewModel.matches.observe(viewLifecycleOwner, Observer { e ->
//            println("${e.size} matches in fragment")
//            recyclerViewMatches.adapter = GroupsDetailAdapter(e, requireContext())
//        })


        return root
    }
}