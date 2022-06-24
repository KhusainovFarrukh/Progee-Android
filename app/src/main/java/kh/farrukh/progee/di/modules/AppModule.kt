package kh.farrukh.progee.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kh.farrukh.progee.data.framework.FrameworkRepository
import kh.farrukh.progee.data.framework.FrameworkRepositoryImpl
import kh.farrukh.progee.data.language.LanguageRepository
import kh.farrukh.progee.data.language.LanguageRepositoryImpl
import kh.farrukh.progee.data.review.ReviewRepository
import kh.farrukh.progee.data.review.ReviewRepositoryImpl
import kh.farrukh.progee.db.CacheDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 *Created by farrukh_kh on 6/19/22 8:25 PM
 *kh.farrukh.progee.di.modules
 **/
@[Module(includes = [AppBindsModule::class]) InstallIn(SingletonComponent::class)]
object AppModule {

    @[Provides Singleton]
    fun provideCacheDatabase(@ApplicationContext context: Context): CacheDatabase =
        Room.databaseBuilder(context, CacheDatabase::class.java, "cache_db.db").build()

    @[Provides IoDispatcher]
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @[Provides MainDispatcher]
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @[Provides DefaultDispatcher]
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @[Provides UnconfinedDispatcher]
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}

@[Module InstallIn(SingletonComponent::class)]
abstract class AppBindsModule {

    @Binds
    abstract fun bindLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl): LanguageRepository

    @Binds
    abstract fun bindFrameworkRepository(frameworkRepositoryImpl: FrameworkRepositoryImpl): FrameworkRepository

    @Binds
    abstract fun bindReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UnconfinedDispatcher
