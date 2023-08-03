package com.yy.baselibrary.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

/**
 * @author YY
 * Created 2022/2/28
 * Description：
 */
object LocaleConfigurationWrapperManager {
    private const val LANGUAGE_ENGLISH = 1
    private const val LANGUAGE_TRADITIONAL_CHINESE = 2
    private const val LANGUAGE_SIMPLIFIED_CHINESE = 3
    private const val KEY_LANGUAGE_ID = "key_language"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun wrapConfiguration(context: Context, config: Configuration): Context {
        return context.createConfigurationContext(config)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun wrapLocale(context: Context, locale: Locale): Context {
        val res: Resources = context.resources
        val config: Configuration = res.configuration
        config.setLocale(locale)
        return wrapConfiguration(context, config)
    }

    fun saveLocaleConfiguration(context: Context?, localeItem: LocaleItem) {
        if (context == null) {
            return
        }
        SharedPreferencesHelper.instance?.save(
            context!!,
            KEY_LANGUAGE_ID,
            localeItem.localeId
        )
    }

    fun getLocaleConfiguration(context: Context?): LocaleItem {
        val languageId: Int =
            SharedPreferencesHelper.instance?.getSPInt(context!!, KEY_LANGUAGE_ID)!!
        if (languageId >= LANGUAGE_ENGLISH || languageId <= LANGUAGE_SIMPLIFIED_CHINESE) {
            return LocaleItem.getLocaleItem(languageId)
        }
        val locale: Locale = Locale.getDefault()
        return if (isSameLocale(locale, Locale.TRADITIONAL_CHINESE)) {
            LocaleItem.TRADITIONAL_CHINESE
        } else if (isSameLocale(locale, Locale.SIMPLIFIED_CHINESE)) {
            LocaleItem.SIMPLIFIED_CHINESE
        } else {
            LocaleItem.ENGLISH
        }
    }

    private fun isSameLocale(firstLocale: Locale, secondLocale: Locale): Boolean {
        return firstLocale.language === secondLocale.language && firstLocale.country === secondLocale.country
    }

    enum class LocaleItem(
        val localeId: Int,
        val localeLanguage: String,
        val localeCountry: String
    ) {
        ENGLISH(
            LANGUAGE_ENGLISH,
            Locale.ENGLISH.language,
            Locale.ENGLISH.country
        ),
        TRADITIONAL_CHINESE(
            LANGUAGE_TRADITIONAL_CHINESE,
            Locale.TRADITIONAL_CHINESE.language,
            Locale.TRADITIONAL_CHINESE.country
        ),
        SIMPLIFIED_CHINESE(
            LANGUAGE_SIMPLIFIED_CHINESE,
            Locale.SIMPLIFIED_CHINESE.language,
            Locale.SIMPLIFIED_CHINESE.country
        );

        companion object {
            fun getLocaleItem(localeId: Int): LocaleItem {
                for (localeItem in values()) {
                    if (localeId == localeItem.localeId) {
                        return localeItem
                    }
                }
                return ENGLISH
            }
        }
    }
}