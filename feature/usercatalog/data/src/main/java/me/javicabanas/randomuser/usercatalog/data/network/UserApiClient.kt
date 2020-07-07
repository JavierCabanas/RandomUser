package me.javicabanas.randomuser.usercatalog.data.network

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.network.ApiClient
import me.javicabanas.randomuser.network.map
import me.javicabanas.randomuser.usercatalog.domain.data.UserNetworkDataSource
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

class UserApiClient(private val userService: UserService) : UserNetworkDataSource, ApiClient {
    override fun getAllUsers(): Either<Failure, List<User>> = apiCall {
        userService.getAllUsers().execute().map { users ->
            users.map { it.toDomain() }
        }
    }

    override fun getUser(userId: String): Either<Failure, User> = apiCall {
        userService.getUser(userId).execute().map { user ->
            user.toDomain()
        }
    }

    override fun deleteUser(userId: String): Either<Failure, Unit> = apiCall {
        userService.deleteUser(userId).execute().map { Unit }
    }
}

interface UserService {
    @GET("/Karumi/codetestdata/users")
    fun getAllUsers(): Call<List<UserApiModel>>

    @GET("/Karumi/codetestdata/users/{userId}")
    fun getUser(@Path("userId") userId: String): Call<UserApiModel>

    @DELETE("/Karumi/codetestdata/users/{userId}")
    fun deleteUser(@Path("userId") userId: String): Call<Unit>
}
