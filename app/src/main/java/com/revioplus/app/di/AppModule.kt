package com.revioplus.app.di

import com.revioplus.app.data.repository.FakeChallengeRepository
import com.revioplus.app.data.repository.FakeRecyclingRepository
import com.revioplus.app.data.repository.FakeUserRepository
import com.revioplus.app.domain.repository.ChallengeRepository
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.UserRepository
import com.revioplus.app.domain.usecase.GetHomeDashboardUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Repositorios fake (pueden cambiarse por Room/remote en el futuro)

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = FakeUserRepository()

    @Provides
    @Singleton
    fun provideRecyclingRepository(): RecyclingRepository = FakeRecyclingRepository()

    @Provides
    @Singleton
    fun provideChallengeRepository(): ChallengeRepository = FakeChallengeRepository()

    // Use case para el dashboard de Inicio

    @Provides
    @Singleton
    fun provideGetHomeDashboardUseCase(
        userRepository: UserRepository,
        recyclingRepository: RecyclingRepository,
        challengeRepository: ChallengeRepository
    ): GetHomeDashboardUseCase {
        return GetHomeDashboardUseCase(
            userRepository = userRepository,
            recyclingRepository = recyclingRepository,
            challengeRepository = challengeRepository
        )
    }
}