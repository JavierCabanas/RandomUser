package me.javicabanas.randomuser.usercatalog.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.javicabanas.randomuser.core.domain.BaseInteractor
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(private val userRepository: UserRepository) :
    BaseInteractor<String, Unit>() {
    override suspend fun run(request: String): Either<Failure, Unit> =
        withContext(Dispatchers.IO) { userRepository.deleteUser(request) }
}
