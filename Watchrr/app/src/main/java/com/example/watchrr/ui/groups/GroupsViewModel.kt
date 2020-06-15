package com.example.watchrr.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.watchrr.data.group.Group
import com.example.watchrr.data.group.GroupRepository
import com.example.watchrr.data.user.Movie
import com.example.watchrr.data.user.User
import com.example.watchrr.data.user.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class GroupsViewModel : ViewModel() {

    private val groupRepository = GroupRepository()
    private val userRepository = UserRepository()

    var currentGroup: MutableLiveData<Group> = MutableLiveData()
    var matchesInGroup: MutableLiveData<List<Movie>> = MutableLiveData()

    fun setCurrentGroup(group: Group) {
        currentGroup.value = group;
        //get matches for group
        GlobalScope.launch {
            groupRepository.getMatchesInGroup(group)
            matchesInGroup.postValue(groupRepository.getMatchesInGroup(group))
        }
    }


}