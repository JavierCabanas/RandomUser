package me.javicabanas.randomuser.usercatalog.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.contains
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import org.junit.Test

class DeleteUserTest {
    private val repository: UserRepository = mockk()
    private val deleteUser = DeleteUser(repository)

    @Test
    fun `should return right when user is deleted`() {
        val userId = givenAUserIdToDelete()
        val result = runBlocking { deleteUser(userId) }
        assert(result is Either.Right)
    }

    private fun givenAUserIdToDelete(): String {
        val userId = UserMother.user.id
        coEvery { repository.deleteUser(any()) } returns Unit.toRight()
        return userId
    }

    @Test
    fun `should propagate error from repository`() {
        val requestId = UserMother.user.id
        val expectedFailure = givenAFailureResponse()
        val result = runBlocking { deleteUser(requestId) }
        assert(result.swap().contains(expectedFailure))
    }

    private fun givenAFailureResponse(): Failure {
        val failure = Failure.Network("any reason")
        coEvery { repository.deleteUser(any()) } returns failure.toLeft()
        return failure
    }
}