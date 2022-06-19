package kh.farrukh.progee.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kh.farrukh.progee.api.language.LanguageApi
import kh.farrukh.progee.utils.BASE_URL_PROGEE_API
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 *Created by farrukh_kh on 6/19/22 8:25 PM
 *kh.farrukh.progee.di.modules
 **/
@[Module(includes = [ApiModule::class]) InstallIn(SingletonComponent::class)]
object NetworkModule {

    @[Singleton Provides]
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        //TODO add auth interceptor
//        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
//            .addInterceptor(apiKeyInterceptor)
            .build()

    @[Singleton Provides]
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_PROGEE_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}

@[Module InstallIn(SingletonComponent::class)]
object ApiModule {

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): LanguageApi = retrofit.create()
}