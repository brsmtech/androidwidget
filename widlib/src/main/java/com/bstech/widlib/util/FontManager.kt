package com.bstech.widlib.util

import android.content.res.AssetManager
import android.graphics.Typeface
import android.util.SparseArray

import com.bstech.widlib.view.FontTextView

/**
 * Created by brayskiy on 2/23/18.
 */

class FontManager {

    fun customFontTypeface(indicator: Int): Typeface {
        return customFontsMap!!.get(indicator)
    }

    companion object {
        private var fontManager: FontManager? = null
        private var customFontsMap: SparseArray<Typeface>? = null
        private var assetManager: AssetManager? = null

        fun init(assets: AssetManager) {
            assetManager = assets
        }

        fun get(): FontManager {
            if (assetManager == null) {
                throw IllegalStateException("FontManager not initialized. Add FontManager.init(AssetManager assets)")
            }

            if (customFontsMap == null) {
                customFontsMap = SparseArray()
                customFontsMap!!.put(FontTextView.TYPEFACE_ICON_FONTS,
                        Typeface.createFromAsset(assetManager, "fonts/icon-fonts.ttf"))
            }

            if (fontManager == null) {
                fontManager = FontManager()
            }
            return fontManager!!
        }
    }
}
