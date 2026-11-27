package com.example.productexplorerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// LAYER: DI (starting point)
// Why: Hilt needs one place to start. Without this class, none of the
// @Inject, @Module or @HiltViewModel code in the app would work.

// @HiltAndroidApp starts Hilt when the app starts.
// It's registered in AndroidManifest.xml with android:name=".ProductExplorerApplication".
@HiltAndroidApp
class ProductExplorerApplication : Application()
