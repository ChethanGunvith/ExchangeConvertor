package com.chethan.currencycalculator.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.chethan.currencycalculator.api.NetWorkApi
import com.chethan.currencycalculator.constants.Constants.API_REST_URL
import com.chethan.currencycalculator.constants.Constants.DATABASE_NAME
import com.chethan.currencycalculator.database.AppDatabase
import com.chethan.currencycalculator.database.dao.CurrencyDao
import com.chethan.currencycalculator.database.dao.ExchangeRateDao
import com.chethan.currencycalculator.repository.CurrencyRepository
import com.chethan.currencycalculator.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideGithubService(): NetWorkApi {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        val retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(API_REST_URL).client(httpClient.build())
                .build()
        return retrofit.create(NetWorkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(db: AppDatabase): CurrencyDao {
        return db.currencyDao()
    }

    @Singleton
    @Provides
    fun provideExchangeRateDao(db: AppDatabase): ExchangeRateDao {
        return db.exchangeRateDaoDao()
    }

    @Provides
    @Singleton
    fun currencyRepository(
        api: NetWorkApi,
        currencyDao: CurrencyDao,
        exchangeRateDao: ExchangeRateDao
    ): CurrencyRepository {
        return CurrencyRepository(api, currencyDao, exchangeRateDao)
    }
}
