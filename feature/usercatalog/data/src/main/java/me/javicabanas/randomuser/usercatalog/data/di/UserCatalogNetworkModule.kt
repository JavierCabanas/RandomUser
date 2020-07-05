package me.javicabanas.randomuser.usercatalog.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.javicabanas.randomuser.network.client.ApiClientBuilder
import me.javicabanas.randomuser.usercatalog.data.network.UserApiClient
import me.javicabanas.randomuser.usercatalog.data.network.UserService
import me.javicabanas.randomuser.usercatalog.domain.data.UserNetworkDataSource
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object UserCatalogNetworkModule {
    @Provides
    @Singleton
    fun provideUserNetworkDataSource(apiClientBuilder: ApiClientBuilder): UserNetworkDataSource =
        UserApiClient(apiClientBuilder.buildEndpoint(UserService::class))
}
