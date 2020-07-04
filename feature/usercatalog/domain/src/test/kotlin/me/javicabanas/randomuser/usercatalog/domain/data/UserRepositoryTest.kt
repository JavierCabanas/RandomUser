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
    private val userRepository = UserRepository(network)

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
}
