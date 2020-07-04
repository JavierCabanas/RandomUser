package me.javicabanas.randomuser.usercatalog.domain.data

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User

class UserRepository(private val userNetwork: UserNetworkDataSource) {
    fun getAllUsers(): Either<Failure, List<User>> = emptyList<User>().toRight()
}

interface UserNetworkDataSource {
    fun getAllUsers(): Either<Failure, List<User>>
}
