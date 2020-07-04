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

class GetAllUsersTest {
    private val userRepository: UserRepository = mockk()
    private val getAllUsers = GetAllUsers(userRepository)

    @Test
    fun `should return list given by repository`() {
        val expectedUsers = givenAlistWithUsers()
        val result = runBlocking { getAllUsers(Unit) }
        assert(result.contains(expectedUsers))
    }

    private fun givenAlistWithUsers(): List<User> {
        val users = UserMother.users
        coEvery { userRepository.getAllUsers() } returns users.toRight()
        return users
    }

    @Test
    fun `should propagate error from repository`() {
        val expectedFailure = givenAFailureResponse()
        val result = runBlocking { getAllUsers(Unit) }
        assert(result.swap().contains(expectedFailure))
    }

    private fun givenAFailureResponse(): Failure {
        val failure = Failure.Network("any reason")
        coEvery { userRepository.getAllUsers() } returns failure.toLeft()
        return failure
    }

    @Test
    fun `should return failure when list is empty`() {
        givenAnEmptyList()
        val result = runBlocking { getAllUsers(Unit) }
        assert(result.swap().exists { it is Failure.ElementNotFound })
    }

    private fun givenAnEmptyList() {
        coEvery { userRepository.getAllUsers() } returns emptyList<User>().toRight()
    }

}