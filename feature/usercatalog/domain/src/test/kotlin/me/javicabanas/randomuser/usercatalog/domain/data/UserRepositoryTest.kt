package me.javicabanas.randomuser.usercatalog.domain.data

import io.mockk.every
import io.mockk.mockk
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.contains
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.UserMother
import org.junit.Test

class UserRepositoryTest {
    private val network: UserNetworkDataSource = mockk()
    private val local: UserLocalDataSource = mockk(relaxed = true)
    private val userRepository = UserRepository(network, local)

    @Test
    fun `should return list given by datasource`() {
        val expectedUsers = givenAlistWithUsers()
        val result = userRepository.getAllUsers()
        assert(result.contains(expectedUsers))
    }

    private fun givenAlistWithUsers(): List<User> {
        val users = UserMother.users
        every { network.getAllUsers() } returns users.toRight()
        return users
    }

    @Test
    fun `should propagate error from datasource`() {
        val expectedFailure = givenAFailureResponse()
        val result = userRepository.getAllUsers()
        assert(result.swap().contains(expectedFailure))
    }

    private fun givenAFailureResponse(): Failure {
        val failure = Failure.Network("any reason")
        every { network.getAllUsers() } returns failure.toLeft()
        return failure
    }

    @Test
    fun `should return user when is stored locally`() {
        val expectedUser = givenAUserFromLocalStorage()
        val result = userRepository.getUser(expectedUser.id)
        assert(result.contains(expectedUser))
    }

    private fun givenAUserFromLocalStorage(): User {
        val user = UserMother.user
        every { local.getUser(any()) } returns user.toRight()
        return user
    }

    @Test
    fun `should return user when is not stored locally but on network`() {
        val expectedUser = givenAUserFromLocalStorage()
        val result = userRepository.getUser(expectedUser.id)
        assert(result.contains(expectedUser))
    }

    private fun givenAUserFromNetwork(): User {
        val user = UserMother.user
        val users = UserMother.users + user
        every { local.getUser(any()) } returns Failure.ElementNotFound("").toLeft()
        every { network.getAllUsers() } returns users.toRight()
        return user
    }

    @Test
    fun `should return failure when user is not found locally or remotely`() {
        val requestId = UserMother.user.id
        val expectedFailure = givenThereIsNoUser()
        val result = userRepository.getUser(requestId)
        assert(result.swap().contains(expectedFailure))
    }

    private fun givenThereIsNoUser(): Failure.ElementNotFound {
        val expectedFailure = Failure.ElementNotFound("")
        every { local.getUser(any()) } returns Failure.ElementNotFound("").toLeft()
        every { network.getAllUsers() } returns expectedFailure.toLeft()
        return expectedFailure
    }
}
