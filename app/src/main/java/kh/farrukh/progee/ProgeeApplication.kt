package kh.farrukh.progee

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 *Created by farrukh_kh on 6/18/22 1:28 PM
 *kh.farrukh.progee
 **/
@HiltAndroidApp
class ProgeeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}