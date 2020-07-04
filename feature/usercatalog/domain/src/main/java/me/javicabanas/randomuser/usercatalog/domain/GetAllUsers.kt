package me.javicabanas.randomuser.usercatalog.domain

import me.javicabanas.randomuser.core.domain.BaseInteractor
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository

class GetAllUsers(private val userRepository: UserRepository) : BaseInteractor<Unit, List<User>>() {
    override suspend fun run(request: Unit): Either<Failure, List<User>> =
        userRepository.getAllUsers()
}