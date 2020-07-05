package me.javicabanas.randomuser.testcommons

import me.javicabanas.randomuser.core.model.User

@Suppress("MagicNumber")
object UserMother {
    val users = (1..10).map {
        User(
            id = "id-$it",
            avatar = "avatar-$it",
            background = "background-$it",
            city = "city-$it",
            firstName = "firstName-$it",
            lastName = "lastName-$it",
            gender = "gender-$it",
            email = "email-$it",
            description = "description-$it"
        )
    }
    val user: User = users.first()
}
