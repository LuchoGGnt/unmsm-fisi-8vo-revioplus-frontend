package com.revioplus.app.data.remote

import com.revioplus.app.data.local.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //Obtener el token del sessMan
        val token = runBlocking {
            sessionManager.authToken.first()
        }
        //Crear una copia del request
        val request = chain.request().newBuilder()

        //Pegar el token en la cabecita
        if(!token.isNullOrBlank())
            request.addHeader("Authorization", "Bearer $token")

        //Pasar la request
        return chain.proceed(request.build())
    }
}