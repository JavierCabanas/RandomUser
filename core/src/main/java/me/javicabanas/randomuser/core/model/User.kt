package me.javicabanas.randomuser.core.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val city: String,
    val avatar: String,
    val background: String,
    val gender: String,
    val email: String,
    val description: String
)
