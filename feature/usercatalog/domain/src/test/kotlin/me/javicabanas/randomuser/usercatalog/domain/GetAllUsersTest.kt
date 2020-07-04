package me.javicabanas.randomuser.usercatalog.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import org.junit.Test

class GetAllUsersTest {
    private val userRepository: UserRepository = mockk()
    private val getAllUsers = GetAllUsers(userRepository)

    @Test
    fun `should return list given by repository`() {
        val expectedUsers = givenAlistWithUsers()
        val result = runBlocking { getAllUsers(Unit) }
        assert(result.exists { it == expectedUsers })
    }

    private fun givenAlistWithUsers(): List<User> {
        val users = (1..10).map {
            User(
                id = "id-$it",
                avatar = "avatar-$it",
                background = "background-$it",
                city = "city-$it",
                firstName = "firstName-$it",
                lastName = "lastName-$it",
                gender = "gender-$it",
                email = "email-$it",
                description = "description-$it"
            )
        }
        coEvery { userRepository.getAllUsers() } returns users.toRight()
        return users
    }

    @Test
    fun `should propagate error from repository`() {
        val expectedFailure = givenAFailureResponse()
        val result = runBlocking { getAllUsers(Unit) }
        assert(result.swap().exists { it == expectedFailure })
    }

    private fun givenAFailureResponse(): Failure {
        val failure = Failure.Network("any reason")
        coEvery { userRepository.getAllUsers() } returns failure.toLeft()
        return failure
    }

}