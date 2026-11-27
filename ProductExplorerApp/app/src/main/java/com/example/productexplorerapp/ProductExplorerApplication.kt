package com.example.productexplorerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// @HiltAndroidApp starts Hilt when the app starts.
// It's registered in AndroidManifest.xml with android:name=".ProductExplorerApplication".
@HiltAndroidApp
class ProductExplorerApplication : Application()
