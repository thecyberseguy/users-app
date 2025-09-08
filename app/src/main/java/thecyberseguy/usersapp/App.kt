package thecyberseguy.usersapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var context: WeakReference<Context>
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(applicationContext)
    }

}