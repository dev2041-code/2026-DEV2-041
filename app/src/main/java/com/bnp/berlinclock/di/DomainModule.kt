package com.bnp.berlinclock.di

import com.bnp.berlinclock.domain.converter.BerlinClockConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    /**
     * Singleton because the converter is stateless and reusable.
     */
    @Provides
    @Singleton
    fun provideBerlinClockConverter(): BerlinClockConverter = BerlinClockConverter()
}
