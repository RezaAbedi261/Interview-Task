@file:Suppress("unused", "MemberVisibilityCanBePrivate", "DEPRECATION", "UNUSED_PARAMETER", "NAME_SHADOWING")

package com.task.interview.utils


import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.view.View
import com.task.interview.data.local.prefs.AppPreferences
import com.task.interview.di.inject
import java.util.*


/**
 * Saves the selected locale by user and force the RTL if the locale is persian
 * We don't read or change device locale, the only thing we change is application locale. We save the
 * last selected locale in the shared preferences and use it when app launches.
 * This class do not use standard locale strings and it has its own locale int (LOCALE_PERSIAN,
 * LOCALE_ENGLISH, LOCALE_FRENCH)
 */
object LocaleHelper {
    /**
     * The constant LOCALE_PERSIAN.
     */
    //endregion

    //region Fields

    //endregion

    //region Setters


    //endregion

    //region Functions


    /**
     * Get the saved locale from Shared pref
     *
     * @return the saved locale (LOCALE_PERSIAN or LOCALE_ENGLISH or LOCALE_FRENCH or Null). The default is persian.
     */
    private val prefs by inject<AppPreferences>()
    val savedLocale get() = prefs.language


    //region Constants
    /**
     * The constant LOCALE_ENGLISH.
     */
    const val LOCALE_ENGLISH = 20
    /**
     * The constant LOCALE_FRENCH.
     */
    const val LOCALE_FRENCH = 30
    /**
     * The constant LOCALE_TURKISH.
     */
    const val LOCALE_TURKISH = 40
    /**
     * The constant LOCALE_ARABIC.
     */
    const val LOCALE_ARABIC = 50
/**
     * The constant LOCALE_PERSIAN.
     */
const val LOCALE_PERSIAN = 60


    /**
     * Gets current active locale string.
     *
     * @return the current active locale string
     */
    val currentActiveLocaleString: String
        get() {
            val activeLocale = savedLocale
            var locale = "fa-IR"
            when (activeLocale) {
                LOCALE_ENGLISH -> locale = "en-GB"
                LOCALE_FRENCH -> locale = "fr-FR"
                LOCALE_TURKISH -> locale = "tr-TR"
                LOCALE_ARABIC -> locale = "ar-IR"
                LOCALE_PERSIAN -> locale = "fa-IR"
            }
            return locale
        }

    /**
     * Gets current active locale language string.
     *
     * @return the current active locale language string
     */
    val currentActiveLocaleLanguageString: String
        get() {
            val activeLocale = savedLocale
            var locale = "fa"
            when (activeLocale) {
                LOCALE_ENGLISH -> locale = "en"
                LOCALE_FRENCH -> locale = "fr"
                LOCALE_TURKISH -> locale = "ug"
                LOCALE_ARABIC -> locale = "ar"
                LOCALE_PERSIAN -> locale = "fa"
            }
            return locale
        }

    /**
     * Gets current active locale country string.
     *
     * @return the current active locale country string
     */
    val currentActiveLocaleCountryString: String
        get() {
            val activeLocale = savedLocale
            var locale = "IR"
            when (activeLocale) {
                LOCALE_ENGLISH -> locale = "GB"
                LOCALE_FRENCH -> locale = "FR"
                LOCALE_TURKISH -> locale = "CN"
                LOCALE_ARABIC -> locale = "IR"
                LOCALE_PERSIAN -> locale = "IR"
            }
            return locale
        }

    /**
     * Gets real current active locale string.
     *
     * @return the real current active locale string
     */
    val realCurrentActiveLocaleString: String
        get() {
            val activeLocale = savedLocale
            var locale = "fa-IR"
            when (activeLocale) {
                LOCALE_ENGLISH -> locale = "en-GB"
                LOCALE_FRENCH -> locale = "fr-FR"
                LOCALE_TURKISH -> locale = "ug-CN"
                LOCALE_ARABIC -> locale = "ar-IR"
                LOCALE_PERSIAN -> locale = "fa-IR"
            }
            return locale
        }

    /**
     * Is current local rtl boolean.
     *
     * @return the boolean
     */
    val isCurrentLocalRtl: Boolean
        get() {
            val activeLocale = savedLocale
            return activeLocale == savedLocale || activeLocale == LOCALE_TURKISH ||
                    activeLocale == LOCALE_ARABIC || activeLocale == LOCALE_PERSIAN
        }

    /**
     * Force RTL or LTR on the given view based on the saved locale
     *
     * @param view the view to be forced.
     */
    fun setLayoutDirectionBasedOnLocale(view: View) {
        val locale = savedLocale
        if (locale == 0) {
            return
        }

        if (locale == savedLocale || locale == LOCALE_TURKISH ||
            locale == LOCALE_ARABIC || locale == LOCALE_PERSIAN) {
            view.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            view.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
    }

    /**
     * Changes the App locale, saves the selected locale on shared pref for future references.
     * If the new locale is equal to current locale, nothing will happen
     *
     * @param context context
     * @param locale  locale value equal to LOCALE_PERSIAN or LOCALE_ENGLISH or LOCALE_FRENCH
     * @return boolean True if locale changed, otherwise false
     */
    fun changeAppLocale(context: Context, locale: Int): Boolean {
        val lang = convertToLocaleLanguageString(locale)

        if (lang.isEmpty() || isCurrentLanguageEqualsToGivenLanguage(context, lang)) {
            return false
        }


        changeAppLocaleFromSharedPrefIfNeeded(context, true)



        return true
    }

    /**
     * Changes the App locale based on what has been saved inside the shared pref,
     * If the saved locale is equal to the current locale, nothing will happen.
     *
     * @param context              context
     * @param shouldFinishActivity boolean
     * @return boolean True if locale changed, otherwise false
     */
    fun changeAppLocaleFromSharedPrefIfNeeded(
        context: Context,
        shouldFinishActivity: Boolean
    ): Boolean {
        val lang = convertToLocaleLanguageString(savedLocale)

        if (lang.isEmpty() || isCurrentLanguageEqualsToGivenLanguage(context, lang)) {
            changeActivityConfiguration(context, Locale(lang), false)
            return false
        }

        changeActivityConfiguration(context, Locale(lang), shouldFinishActivity)

        return true
    }

    /**
     * Is current language equals to given language boolean.
     *
     *
     *
     * @param context  context
     * @param language the language
     * @return the boolean
     */
    internal fun isCurrentLanguageEqualsToGivenLanguage(
        context: Context,
        language: String
    ): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        language == context.resources.configuration.locales.get(0).language
    } else {
        val l = context.resources.configuration.locale.language
        language == l
    }


    /**
     * Convert the locale code to standard locale language string.
     *
     * @param locale the locale code
     * @return the standard language string
     */
    internal fun convertToLocaleLanguageString(locale: Int?): String {
        if (locale == null) {
            return ""
        }

        return when (locale) {
            LOCALE_ENGLISH -> {
                "en"
            }
            LOCALE_FRENCH -> {
                "fr"
            }
            LOCALE_TURKISH -> {
                "ug"
            }
            LOCALE_ARABIC -> {
                "ar"
            }
            LOCALE_PERSIAN -> {
                "fa"
            }
            else -> {
                ""
            }
        }
    }


    /**
     * Changes activity configuration.
     *
     * @param context              context
     * @param locale               the new locale
     * @param shouldFinishActivity the boolean
     */
    internal fun changeActivityConfiguration(
        context: Context,
        locale: Locale,
        shouldFinishActivity: Boolean
    ) {
        Locale.setDefault(locale)
        val res = context.applicationContext.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocales( LocaleList(locale))
        }
        if (context is Activity) {
            context.baseContext.resources.updateConfiguration(config, res.displayMetrics)
            if (shouldFinishActivity)
            {
                val mStartActivity = Intent(context, context.javaClass)
                context.startActivity(mStartActivity)
                context.overridePendingTransition(0,0)
                context.finish()
            }
        } else if (context is Application) {
            context.baseContext.resources.updateConfiguration(config, res.displayMetrics)
        }
        context.resources.updateConfiguration(config, res.displayMetrics)
    }

    fun setLocale(application: Application?) {
        if (application == null) {
            return
        }
        val lang: String = "fa"
        val country: String = "IR"
        val language =
            application.baseContext.resources.configuration.locale.language
        if (language.equals(lang, ignoreCase = true)) {
            updateBaseContextWithDefaultLocale(application)
            return
        }
        val locale = Locale(lang, country)
        Locale.setDefault(locale)
        updateBaseContextWithDefaultLocale(application)
    }

    /**
     * Change locale in context context.
     *
     * @param context the context
     * @return the context
     */
    fun changeLocaleInContext(context: Context?): Context? {
        var context = context

        if (context != null) {
            try {
                val lang = currentActiveLocaleLanguageString
                val country = currentActiveLocaleCountryString
                context = LocaleContextWrapper.wrap(context, Locale(lang, country))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return context
    }


    private fun updateBaseContextWithDefaultLocale(application: Application) {
        val res = application.baseContext.resources
        val conf = Configuration(res.configuration)
        conf.locale = Locale.getDefault()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conf.setLocales(LocaleList(Locale.getDefault()))
        }
        res.updateConfiguration(conf, res.displayMetrics)
    }

    //endregion

}
