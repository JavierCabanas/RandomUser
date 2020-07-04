package me.javicabanas.randomuser.usercatalog.data.network

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.contains
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.network.client.ApiClientBuilder
import me.javicabanas.randomuser.network.client.HttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserApiClientTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var userApiClient: UserApiClient
    private val httpClient: HttpClient = HttpClient()

    @Before
    @Throws
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val userService = ApiClientBuilder(
            mockWebServer.url("/").toString(),
            httpClient
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
    fun `should return the expected list of users when call to getProductList`() {
        val expectedUserList = givenASuccessfullUsersResponse()
        val response = userApiClient.getAllUsers()

        assert(response.contains(expectedUserList))
    }

    private fun givenASuccessfullUsersResponse(): List<User> {
        val user = User(
            id = "id",
            firstName = "firstName",
            lastName = "lastName",
            city = "city",
            avatar = "avatar",
            background = "background",
            gender = "gender",
            email = "email",
            description = "description"
        )
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
    fun `should return Failure object when call to getProductList fails`() {
        val expectedFailure = givenAnyFailedResponse()
        val response = userApiClient.getAllUsers()

        assert(response.swap().contains(expectedFailure))
    }

    private fun givenAnyFailedResponse(): Failure {
        val body = """
            {
             error_message: "Not Found"
            }
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse()
                .setBody(body)
                .setResponseCode(404)
        )
        return Failure.ElementNotFound(reason = body)
    }
}