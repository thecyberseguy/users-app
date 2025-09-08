package thecyberseguy.usersapp.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import thecyberseguy.usersapp.network.NetworkChecker

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNetworkChecker(@ApplicationContext context: Context) : NetworkChecker {
        return NetworkChecker(context)
    }

}