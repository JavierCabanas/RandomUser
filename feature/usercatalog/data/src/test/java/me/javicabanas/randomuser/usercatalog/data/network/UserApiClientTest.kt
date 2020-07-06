package me.javicabanas.randomuser.usercatalog.data.network

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.functional.contains
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.network.client.ApiClientBuilder
import me.javicabanas.randomuser.testcommons.UserMother
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserApiClientTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var userApiClient: UserApiClient

    @Before
    @Throws
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val userService = ApiClientBuilder(
            mockWebServer.url("/").toString()
        ).buildEndpoint(UserService::class)
        userApiClient = UserApiClient(userService)
    }

    @After
    @Throws
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAllUsers is a GET request`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        userApiClient.getAllUsers()

        Assert.assertEquals("GET", mockWebServer.takeRequest().method)
    }

    @Test
    fun `should return the expected list of users when call to getAllUsers`() {
        val expectedUserList = givenASuccessfullUsersResponse()
        val response = userApiClient.getAllUsers()

        assert(response.contains(expectedUserList))
    }

    private fun givenASuccessfullUsersResponse(): List<User> {
        val user = UserMother.user
        val responseBody = with(user) {
            """
                [
                    {
                      "id": "$id",
                      "first_name": "$firstName",
                      "last_name": "$lastName",
                      "email": "$email",
                      "gender": "$gender",
                      "ip_address": "174.193.154.100",
                      "avatar": "$avatar",
                      "city": "$city",
                      "background": "$background",
                      "description": "$description"
                    }
                ]
            """.trimIndent()
        }
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )
        return listOf(user)
    }

    @Test
    fun `should return Failure object when call to getAllUsers fails`() {
        val expectedFailure = givenAnyFailedResponse()
        val response = userApiClient.getAllUsers()

        assert(response.swap().contains(expectedFailure))
    }

    private fun givenAnyFailedResponse(): Failure {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
        )
        return Failure.ElementNotFound("")
    }

    @Test
    fun `should return the expected user when call to getUser`() {
        val user = givenASuccessfullUserResponse()
        val response = userApiClient.getUser(user.id)

        assert(response.contains(user))
    }

    private fun givenASuccessfullUserResponse(): User {
        val user = UserMother.user
        val responseBody = with(user) {
            """
                    {
                      "id": "$id",
                      "first_name": "$firstName",
                      "last_name": "$lastName",
                      "email": "$email",
                      "gender": "$gender",
                      "ip_address": "174.193.154.100",
                      "avatar": "$avatar",
                      "city": "$city",
                      "background": "$background",
                      "description": "$description"
                    }
            """.trimIndent()
        }
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )
        return user
    }

    @Test
    fun `should return Failure object when call to getUser fails`() {
        val expectedFailure = givenAnyFailedResponse()
        val response = userApiClient.getUser(UserMother.user.id)

        assert(response.swap().contains(expectedFailure))
    }

    @Test
    fun `should return Right when call to deleteUser`() {
        val user = givenASuccessfullDeleteResponse()
        val response = userApiClient.deleteUser(user.id)

        assert(response is Either.Right)
    }

    private fun givenASuccessfullDeleteResponse(): User {
        val user = UserMother.user
        val responseBody =
            """
                {}
            """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )
        return user
    }
}