package com.example.watchrr.data.group

import com.example.watchrr.data.user.User
import java.util.*
import kotlin.collections.ArrayList

data class Group(
    var users: ArrayList<User> = arrayListOf<User>()
) {
    constructor() : this(arrayListOf<User>())
    var uuid: String = UUID.randomUUID().toString()

    fun addUser(user : User){
        users.add(user)
    }
}

