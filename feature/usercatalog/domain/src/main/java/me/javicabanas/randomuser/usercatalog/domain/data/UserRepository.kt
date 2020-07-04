package me.javicabanas.randomuser.usercatalog.domain.data

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val network: UserNetworkDataSource) {
    fun getAllUsers(): Either<Failure, List<User>> = network.getAllUsers()
}

interface UserNetworkDataSource {
    fun getAllUsers(): Either<Failure, List<User>>
}
