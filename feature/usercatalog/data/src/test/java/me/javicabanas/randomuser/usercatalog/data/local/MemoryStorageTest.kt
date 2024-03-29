package me.javicabanas.randomuser.usercatalog.data.local

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.contains
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.UserMother
import org.junit.Test

class MemoryStorageTest {
    @Test
    fun `should return a Failure when user is not stored`() {
        val memoryStorage = givenAnEmptyStorage()
        val result = memoryStorage.getUser(UserMother.user.id)
        assert(result.swap().exists { it is Failure.ElementNotFound })
    }

    private fun givenAnEmptyStorage(): MemoryStorage = MemoryStorage()

    @Test
    fun `should return expected user when it is stored`() {
        val expectedUser = UserMother.user
        val memoryStorage = givenAStorageWithUsers(expectedUser)
        val result = memoryStorage.getUser(UserMother.user.id)
        assert(result.contains(expectedUser))
    }

    private fun givenAStorageWithUsers(expectedUser: User): MemoryStorage =
        MemoryStorage().apply { setUserList(listOf(expectedUser)) }

    @Test
    fun `should return a Failure when there are not deleted users`() {
        val memoryStorage = givenAnEmptyStorage()
        val result = memoryStorage.getDeletedUsersIds()
        assert(result.swap().exists { it is Failure.ElementNotFound })
    }

    @Test
    fun `should return list of ids when there are deleted Users`() {
        val expectedUserId = UserMother.user.id
        val memoryStorage = givenAStorageWithDeletedUsers(expectedUserId)
        val result = memoryStorage.getDeletedUsersIds()
        assert(result.exists { it.contains(expectedUserId) })
    }

    private fun givenAStorageWithDeletedUsers(expectedUserId: String): MemoryStorage =
        MemoryStorage().apply { deleteUser(expectedUserId) }
}