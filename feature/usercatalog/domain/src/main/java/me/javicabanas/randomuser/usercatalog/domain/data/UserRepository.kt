package me.javicabanas.randomuser.usercatalog.domain.data

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.flatMapLeft
import me.javicabanas.randomuser.core.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val network: UserNetworkDataSource,
    private val local: LocalDataSource
) {
    fun getAllUsers(): Either<Failure, List<User>> = network.getAllUsers()
        .also {
            it.map { users ->
                local.setAllUsers(users)
            }
        }

    fun getUser(request: String): Either<Failure, User> {
        return local.getUser(request).flatMapLeft {
            network.getAllUsers()
                .map { users ->
                    users.first { it.id == request }
                }
        }
    }
}

interface UserNetworkDataSource {
    fun getAllUsers(): Either<Failure, List<User>>
}

interface LocalDataSource {
    fun getUser(userId: String): Either<Failure, User>
    fun setAllUsers(users: List<User>)
}
