package me.javicabanas.randomuser.usercatalog.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.javicabanas.randomuser.core.domain.BaseInteractor
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.flatMap
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import javax.inject.Inject

class GetAllUsers @Inject constructor(private val userRepository: UserRepository) :
    BaseInteractor<Unit, List<User>>() {
    override suspend fun run(request: Unit): Either<Failure, List<User>> =
        withContext(Dispatchers.IO) {
            userRepository.getAllUsers().flatMap { it.toFailureIfEmpty() }
        }
}

private fun <S> List<S>.toFailureIfEmpty(): Either<Failure, List<S>> =
    if (isEmpty()) Either.Left(Failure.ElementNotFound("Empty list")) else Either.Right(this)
