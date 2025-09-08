package thecyberseguy.usersapp.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import thecyberseguy.usersapp.constants.Url
import thecyberseguy.usersapp.network.APIInterface
import thecyberseguy.usersapp.network.APIRepository
import thecyberseguy.usersapp.network.NetworkChecker
import thecyberseguy.usersapp.network.RequestInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .client(okHttp)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: RequestInterceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideApiRepository(api: APIInterface, network: NetworkChecker): APIRepository {
        return APIRepository(api, network)
    }

}