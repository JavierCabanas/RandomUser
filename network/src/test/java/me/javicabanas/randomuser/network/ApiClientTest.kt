package me.javicabanas.randomuser.network

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonException
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import org.junit.Test
import java.io.IOException

class ApiClientTest {
    private val apiClient = object : ApiClient {}

    @Test
    fun `should return Right when execution is correct`() {
        val result = apiClient.apiCall { Either.Right("success") }
        assert(result.isRight())
    }

    @Test
    fun `should return Network Failure when an IOException is thrown`() {
        val result: Either<Failure, String> = apiClient.apiCall { throw IOException("failure") }
        assert(result.swap().exists { it is Failure.Network })
    }

    @Test
    fun `should return Network Failure when a SerializationException is thrown`() {
        val result: Either<Failure, String> =
            apiClient.apiCall { throw SerializationException("failure") }
        assert(result.swap().exists { it is Failure.Network })
    }

    @Test
    fun `should return Network Failure when a JsonException is thrown`() {
        val result: Either<Failure, String> =
            apiClient.apiCall { throw JsonException("failure") }
        assert(result.swap().exists { it is Failure.Network })
    }
}