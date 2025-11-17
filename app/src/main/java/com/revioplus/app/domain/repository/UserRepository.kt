package com.revioplus.app.domain.repository

import com.revioplus.app.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<Usuario?>
}