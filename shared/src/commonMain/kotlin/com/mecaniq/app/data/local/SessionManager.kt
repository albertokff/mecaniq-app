package com.mecaniq.app.data.local

import com.mecaniq.app.data.models.AuthResponse

object SessionManager {
    var userSession: AuthResponse? = null
        private set

    fun saveSession(session: AuthResponse) {
        userSession = session
    }

    fun clearSession() {
        userSession = null
    }

    fun isUserLoggedIn(): Boolean {
        return userSession != null && !userSession?.token.isNullOrEmpty()
    }

    fun getToken(): String? = userSession?.token
    fun getOficinaId(): String? = userSession?.oficinaId
}