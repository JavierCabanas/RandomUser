package me.javicabanas.randomuser.usercatalog.userlist

import me.javicabanas.randomuser.core.model.User

data class UserUiItem(
    val id: String,
    val name: String,
    val city: String,
    val avatar: String,
    val background: String
)

fun User.toUi(): UserUiItem = UserUiItem(
    id = id,
    name = "$firstName $lastName",
    city = city,
    avatar = avatar,
    background = background
)
