package me.javicabanas.randomuser.usercatalog.userlist

import me.javicabanas.randomuser.core.model.User

data class UserUiModel(
    val id: String,
    val firstName: String,
    val lastName: String,
    val city: String,
    val avatar: String,
    val background: String
)

fun User.toUi(): UserUiModel = UserUiModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    city = city,
    avatar = avatar,
    background = background
)
