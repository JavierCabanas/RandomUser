package me.javicabanas.randomuser.usercatalog.data.network

import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.Either
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.network.ApiClient
import me.javicabanas.randomuser.network.map
import me.javicabanas.randomuser.usercatalog.domain.data.UserNetworkDataSource
import retrofit2.Call
import retrofit2.http.GET

class UserApiClient(private val userService: UserService) : UserNetworkDataSource, ApiClient {
    override fun getAllUsers(): Either<Failure, List<User>> = apiCall {
        userService.getAllUsers().execute().map { users ->
            users.map { it.toDomain() }
        }
    }
}

interface UserService {
    @GET("/users")
    fun getAllUsers(): Call<List<UserApiModel>>
}
