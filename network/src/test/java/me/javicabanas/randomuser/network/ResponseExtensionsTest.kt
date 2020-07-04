package me.javicabanas.randomuser.network

import me.javicabanas.randomuser.core.failure.Failure
import org.junit.Test
import retrofit2.Response

class ResponseExtensionsTest {
    @Test
    fun `should return Failure Network when body is null`() {
        val response: Response<String> = Response.success(null)
        val result = response.map { it }
        assert(result.swap().exists { it is Failure.Network })
    }
}
