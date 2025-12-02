package com.revioplus.app.di

import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.repository.FakeChallengeRepository
import com.revioplus.app.data.repository.FakeRecyclingRepository
import com.revioplus.app.data.repository.FakeWalletRepository
import com.revioplus.app.data.repository.UserRepositoryImpl
import com.revioplus.app.domain.repository.ChallengeRepository
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.UserRepository
import com.revioplus.app.domain.repository.WalletRepository
import com.revioplus.app.domain.usecase.GetHomeDashboardUseCase
import com.revioplus.app.domain.usecase.GetProfileDataUseCase
import com.revioplus.app.domain.usecase.RegisterDepositUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Repositorios

    @Provides
    @Singleton
    fun provideUserRepository(api: ReVioApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRecyclingRepository(): RecyclingRepository = FakeRecyclingRepository()

    @Provides
    @Singleton
    fun provideChallengeRepository(): ChallengeRepository = FakeChallengeRepository()

    @Provides
    @Singleton
    fun provideWalletRepository(): WalletRepository = FakeWalletRepository()

    // Use cases

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

    @Provides
    @Singleton
    fun provideRegisterDepositUseCase(
        userRepository: UserRepository,
        recyclingRepository: RecyclingRepository,
        challengeRepository: ChallengeRepository
    ): RegisterDepositUseCase {
        return RegisterDepositUseCase(
            userRepository = userRepository,
            recyclingRepository = recyclingRepository,
            challengeRepository = challengeRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetProfileDataUseCase(
        userRepository: UserRepository,
    ): GetProfileDataUseCase {
        return GetProfileDataUseCase(
            userRepository = userRepository,
        )
    }

//    @Provides
//    @Singleton
//    fun provideGetWalletForCurrentUserUseCase(
//        userRepository: UserRepository,
//        walletRepository: WalletRepository
//    ): GetWalletForCurrentUserUseCase {
//        return GetWalletForCurrentUserUseCase(
//            userRepository = userRepository,
//            walletRepository = walletRepository
//        )
//    }
}
