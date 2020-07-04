package me.javicabanas.randomuser.core.failure

sealed class Failure(open val reason: String, open val exception: Exception? = null) {
    data class ElementNotFound(
        override val reason: String,
        override val exception: Exception? = null
    ) : Failure(reason, exception)

    data class UnableToRead(
        override val reason: String,
        override val exception: Exception? = null
    ) : Failure(reason, exception)

    data class UnableToWrite(
        override val reason: String,
        override val exception: Exception? = null
    ) : Failure(reason, exception)

    data class MalformedEntity(
        override val reason: String,
        override val exception: Exception? = null
    ) : Failure(reason, exception)

    data class Unknown(override val reason: String, override val exception: Exception? = null) :
        Failure(reason, exception)

    data class Network(override val reason: String, override val exception: Exception? = null) :
        Failure(reason, exception)
}
