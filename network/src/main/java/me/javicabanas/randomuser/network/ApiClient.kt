package me.javicabanas.randomuser.network

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonException
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import java.io.IOException

interface ApiClient {
    fun <T> apiCall(f: () -> Either<Failure, T>): Either<Failure, T> {
        return try {
            f()
        } catch (exception: IOException) {
            Either.Left(
                buildNetworkFailure(
                    reason = exception.localizedMessage,
                    exception = exception
                )
            )
        } catch (exception: SerializationException) {
            Either.Left(
                buildNetworkFailure(
                    reason = exception.localizedMessage,
                    exception = exception
                )
            )
        } catch (exception: JsonException) {
            Either.Left(
                buildNetworkFailure(
                    reason = exception.localizedMessage,
                    exception = exception
                )
            )
        }
    }
}

