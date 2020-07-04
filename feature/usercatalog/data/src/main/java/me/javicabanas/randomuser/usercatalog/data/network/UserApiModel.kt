package me.javicabanas.randomuser.usercatalog.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.javicabanas.randomuser.core.model.User

@Serializable
data class UserApiModel(
    val id: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val city: String,
    val avatar: String,
    val background: String,
    val gender: String,
    val email: String,
    val description: String,
    @SerialName("ip_address") val ipAddress: String
)

internal fun UserApiModel.toDomain(): User =
    User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        city = city,
        avatar = avatar,
        background = background,
        gender = gender,
        email = email,
        description = description
    )