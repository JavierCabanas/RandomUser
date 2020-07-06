package me.javicabanas.randomuser.usercatalog.domain.data

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.flatMapLeft
import me.javicabanas.randomuser.core.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val network: UserNetworkDataSource,
    private val local: UserLocalDataSource
) {
    fun getAllUsers(): Either<Failure, List<User>> = network.getAllUsers()
        .map { users ->
            filterDeletedLocally(users)
        }.also {
            persistUsers(it)
        }

    private fun filterDeletedLocally(users: List<User>): List<User> {
        return local.getDeletedUsersIds().fold(
            ifLeft = {
                users
            }, ifRight = { deletedIds ->
                users.filterNot { deletedIds.contains(it.id) }
            })
    }

    private fun persistUsers(maybeUsers: Either<Failure, List<User>>) {
        maybeUsers.map { users ->
            local.setUserList(users)
        }
    }

    fun getUser(request: String): Either<Failure, User> = local.getUser(request)
        .flatMapLeft {
            network.getUser(request)
        }

    fun deleteUser(userId: String): Either<Failure, Unit> =
        network.deleteUser(userId)
            .also {
                it.map { local.deleteUser(userId) }
            }


}

interface UserNetworkDataSource {
    fun getAllUsers(): Either<Failure, List<User>>
    fun getUser(userId: String): Either<Failure, User>
    fun deleteUser(userId: String): Either<Failure, Unit>
}

interface UserLocalDataSource {
    fun getAllUsers(): Either<Failure, List<User>>
    fun getUser(userId: String): Either<Failure, User>
    fun setUserList(users: List<User>)
    fun deleteUser(userId: String): Either<Failure, Unit>
    fun getDeletedUsersIds(): Either<Failure, List<String>>
}

