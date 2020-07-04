package me.javicabanas.randomuser.network

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import okhttp3.ResponseBody
import retrofit2.Response

private typealias Status = Int
private typealias Message = String

fun <T, R> Response<T>.map(f: (T) -> R): Either<Failure, R> = either(
    onFailure = { status, message, responseBody ->
        val reason = responseBody?.string() ?: message
        buildNetworkFailure(HttpError.fromStatusCode(status), reason)
    },
    onSuccess = { f(it) }
)

private fun <T, R> Response<T>.either(
    onFailure: (Status, Message, ResponseBody?) -> Failure,
    onSuccess: (T) -> R
): Either<Failure, R> = when {
    this.isSuccessful ->
        body()?.let { onSuccess(it).toRight() } ?: Failure.Network("null body").toLeft()
    else -> onFailure(this.code(), this.message(), this.errorBody()).toLeft()
}
