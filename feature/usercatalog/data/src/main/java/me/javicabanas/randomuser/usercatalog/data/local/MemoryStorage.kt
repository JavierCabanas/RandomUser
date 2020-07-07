package me.javicabanas.randomuser.usercatalog.data.local

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.usercatalog.domain.data.UserLocalDataSource

class MemoryStorage : UserLocalDataSource {
    private val users: MutableMap<String, User> = mutableMapOf()
    private val deletedUsers: MutableList<String> = mutableListOf()
    override fun getAllUsers(): Either<Failure, List<User>> {
        return if (users.isEmpty()) {
            Failure.ElementNotFound("Not found on memory storage").toLeft()
        } else {
            users.values.toList().toRight()
        }
    }

    override fun getUser(userId: String): Either<Failure, User> {
        return users[userId]?.toRight() ?: Failure.ElementNotFound("").toLeft()
    }

    override fun setUserList(users: List<User>) {
        users.map { this.users[it.id] = it }
    }

    override fun deleteUser(userId: String): Either<Failure, Unit> {
        users.remove(userId)
        deletedUsers.add(userId)
        return Unit.toRight()
    }

    override fun getDeletedUsersIds(): Either<Failure, List<String>> {
        return if (deletedUsers.isEmpty()) {
            Failure.ElementNotFound("Not found on memory storage").toLeft()
        } else {
            deletedUsers.toList().toRight()
        }
    }
}
