package me.javicabanas.randomuser.usercatalog.domain.data

import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
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
    fun `should return list given by network datasource when ther is no deleted users`() {
        val expectedUsers = givenAlistWithUsers()
        val result = userRepository.getAllUsers()
        assert(result.contains(expectedUsers))
    }

    private fun givenAlistWithUsers(): List<User> {
        val users = UserMother.users
        every { network.getAllUsers() } returns users.toRight()
        every { local.getDeletedUsersIds() } returns Failure.ElementNotFound("").toLeft()
        return users
    }

    @Test
    fun `getAllUsers should not return deleted users`() {
        val expectedUsers = givenAListWithoutDeletedUsers()
        val result = userRepository.getAllUsers()
        assert(result.contains(expectedUsers))
    }

    private fun givenAListWithoutDeletedUsers(): List<User> {
        val allUsers = UserMother.users
        val deletedUsersIds = UserMother.deletedUserIds
        every { network.getAllUsers() } returns allUsers.toRight()
        every { local.getDeletedUsersIds() } returns deletedUsersIds.toRight()
        return allUsers.filterNot { deletedUsersIds.contains(it.id) }
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
        val expectedUser = givenAUserFromNetwork()
        val result = userRepository.getUser(expectedUser.id)
        assert(result.contains(expectedUser))
    }

    private fun givenAUserFromNetwork(): User {
        val user = UserMother.user
        every { local.getUser(any()) } returns Failure.ElementNotFound("").toLeft()
        every { network.getUser(user.id) } returns user.toRight()
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
        every { network.getUser(any()) } returns expectedFailure.toLeft()
        return expectedFailure
    }

    @Test
    fun `should return right when is remotely deleted`() {
        val userId = givenADeletedUserId()
        val result = userRepository.deleteUser(userId)
        assert(result is Either.Right)
    }

    private fun givenADeletedUserId(): String {
        val userId = UserMother.user.id
        every { network.deleteUser(userId) } returns Unit.toRight()
        return userId
    }

    @Test
    fun `should return failure when user is no remotely deleted`() {
        val expectedFailure = givenUserIsNotDeleted()
        val result = userRepository.deleteUser(UserMother.user.id)
        assert(result.swap().contains(expectedFailure))
    }

    private fun givenUserIsNotDeleted(): Failure {
        val failure = Failure.Network("")
        every { network.deleteUser(any()) } returns failure.toLeft()
        return failure
    }

    @Test
    fun `should delete user localy when deleted remotely`() {
        val userId = givenADeletedUserId()
        userRepository.deleteUser(userId)
        verify { local.deleteUser(any()) }
    }

    @Test
    fun `should Not delete user localy when is not deleted remotely`() {
        givenUserIsNotDeleted()
        userRepository.deleteUser(UserMother.user.id)
        verify { local.deleteUser(any()) wasNot called }
    }
}
