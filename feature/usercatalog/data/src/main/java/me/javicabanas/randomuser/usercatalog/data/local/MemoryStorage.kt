package me.javicabanas.randomuser.usercatalog.data.local

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.usercatalog.domain.data.UserLocalDataSource

class MemoryStorage : UserLocalDataSource {
    private val users: MutableMap<String, User> = mutableMapOf()
    override fun getUser(userId: String): Either<Failure, User> {
        return users[userId]?.toRight() ?: Failure.ElementNotFound("").toLeft()
    }

    override fun setAllUsers(users: List<User>) {
        users.map { this.users[it.id] = it }
    }
}
