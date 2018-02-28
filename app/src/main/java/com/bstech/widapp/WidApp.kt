package com.bstech.widapp

import android.app.Application
import com.bstech.widlib.util.FontManager

/**
 * Created by brayskiy on 2/22/18.
 */

class WidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FontManager.init(assets)
    }
}