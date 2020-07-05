package me.javicabanas.randomuser.usercatalog.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.contains
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import org.junit.Test

class GetUserTest {
    private val userRepository: UserRepository = mockk()
    private val getUser = GetUser(userRepository)

    @Test
    fun `should return user given by repository`() {
        val expectedUser = givenAUser()
        val result = runBlocking { getUser(expectedUser.id) }
        assert(result.contains(expectedUser))
    }

    private fun givenAUser(): User {
        val user = UserMother.user
        coEvery { userRepository.getUser(any()) } returns user.toRight()
        return user
    }

    @Test
    fun `should propagate error from repository`() {
        val requestId = UserMother.user.id
        val expectedFailure = givenAFailureResponse()
        val result = runBlocking { getUser(requestId) }
        assert(result.swap().contains(expectedFailure))
    }

    private fun givenAFailureResponse(): Failure {
        val failure = Failure.Network("any reason")
        coEvery { userRepository.getUser(any()) } returns failure.toLeft()
        return failure
    }

}