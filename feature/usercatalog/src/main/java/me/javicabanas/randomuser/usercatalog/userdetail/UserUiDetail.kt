package me.javicabanas.randomuser.usercatalog.userdetail

import me.javicabanas.randomuser.core.model.User

data class UserUiDetail(
    val name: String,
    val gender: String,
    val city: String,
    val email: String,
    val description: String,
    val avatar: String
)

fun User.toDetailUi(): UserUiDetail =
    UserUiDetail(
        name = "$firstName $lastName",
        gender = gender,
        city = city,
        email = email,
        description = description,
        avatar = avatar
    )
