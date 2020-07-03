package me.javicabanas.randomuser.core.domain

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either

abstract class BaseInteractor<in Request, out Response> where Response : Any {
    protected abstract suspend fun run(request: Request): Either<Failure, Response>
    suspend operator fun invoke(request: Request): Either<Failure, Response> {
        return run(request)
    }
}
