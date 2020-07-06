package me.javicabanas.randomuser.usercatalog.domain

import me.javicabanas.randomuser.core.domain.BaseInteractor
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository

class DeleteUser(private val userRepository: UserRepository) : BaseInteractor<String, Unit>() {
    override suspend fun run(request: String): Either<Failure, Unit> =
        userRepository.deleteUser(request)
}