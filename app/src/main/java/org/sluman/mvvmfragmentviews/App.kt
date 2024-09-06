package org.sluman.mvvmfragmentviews

import android.app.Application
import org.sluman.mvvmfragmentviews.di.AppContainer

class App: Application() {
    val appContainer = AppContainer(this)
}