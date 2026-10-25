package com.mecaniq.app.di

import com.mecaniq.app.data.repository.AuthRepository
import com.mecaniq.app.data.repository.AuthRepositoryImpl
import com.mecaniq.app.domain.service.AuthService
import com.mecaniq.app.network.ApiClient
import com.mecaniq.app.network.createAuthApi
import com.mecaniq.app.ui.auth.LoginViewModel

object AppModule {
    private val authApi by lazy { ApiClient.ktorfit.createAuthApi() }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApi)
    }

    val authService: AuthService by lazy {
        AuthService(authRepository)
    }

    fun provideLoginViewModel(): LoginViewModel {
        return LoginViewModel(authService)
    }
}
