package me.javicabanas.randomuser.network.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.javicabanas.randomuser.network.API_BASE_URL
import me.javicabanas.randomuser.network.client.ApiClientBuilder
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiClientBuilder(): ApiClientBuilder =
        ApiClientBuilder(API_BASE_URL)
}
