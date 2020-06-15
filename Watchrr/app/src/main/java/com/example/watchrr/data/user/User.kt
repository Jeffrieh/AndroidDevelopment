package com.example.watchrr.data.user

import com.example.watchrr.data.group.Group
import java.util.*
import kotlin.collections.ArrayList

data class User(
    var uuid: String = UUID.randomUUID().toString(),
    val name: String = "",
    val email: String = "",
    val movies : ArrayList<String> = arrayListOf()
) {
    constructor() : this(UUID.randomUUID().toString(), "", "")

}
