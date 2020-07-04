package me.javicabanas.randomuser.network

import me.javicabanas.randomuser.core.failure.Failure

internal fun buildNetworkFailure(
    httpError: HttpError = HttpError.UNKNOWN,
    reason: String = "",
    exception: Exception? = null
): Failure {
    return when (httpError) {
        HttpError.NOT_FOUND -> Failure.ElementNotFound(reason, exception)
        else -> Failure.Network(reason, exception)
    }
}

@Suppress("MagicNumber")
enum class HttpError(val statusCode: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    CONFLICT(409),
    TEAPOT(418),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    SERVICE_NOT_AVAILABLE(503),
    UNKNOWN(0);

    companion object {
        fun fromStatusCode(statusCode: Int): HttpError =
            values().firstOrNull { statusCode == it.statusCode } ?: UNKNOWN
    }
}
