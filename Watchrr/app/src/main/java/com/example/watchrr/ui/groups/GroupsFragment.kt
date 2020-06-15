package com.example.watchrr.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchrr.R
import com.example.watchrr.data.group.Group
import com.example.watchrr.ui.discovery.HomeViewModel
import kotlinx.android.synthetic.main.fragment_groups.view.*
import kotlinx.android.synthetic.main.fragment_test.view.btnNewGroup
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class GroupsFragment : Fragment() {

    private lateinit var friendsViewModel: GroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_groups, container, false)
        val homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        val friendsViewModel =
            ViewModelProviders.of(requireActivity()).get(GroupsViewModel::class.java)

        val recyclerViewFriends: RecyclerView = root.findViewById(R.id.rvFriends);

        recyclerViewFriends.layoutManager = LinearLayoutManager(root.context)


        root.btnNewGroup.setOnClickListener {
            var email = root.etEmail.text;
            GlobalScope.launch {
                var user = homeViewModel.getUserByEmail(email.toString())
                if(user != null){
                    homeViewModel.makeGroup(arrayListOf(homeViewModel.user.value!!, user))
                }
            }
        }

        homeViewModel.groups.observe(viewLifecycleOwner, Observer {
            println(it.size)
            recyclerViewFriends.adapter = GroupsAdapter(it as ArrayList<Group>, root.context) {
                friendsViewModel.setCurrentGroup(it);
                findNavController().navigate(R.id.nav_friends_detail)
            }
        })

        return root
    }
}