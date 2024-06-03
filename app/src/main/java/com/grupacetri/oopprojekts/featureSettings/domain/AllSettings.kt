package com.grupacetri.oopprojekts.featureSettings.domain

import androidx.annotation.StringRes
import com.grupacetri.oopprojekts.R
import com.grupacetri.oopprojekts.Settings
import com.grupacetri.oopprojekts.featureSettings.data.SettingsCategory
import com.grupacetri.oopprojekts.featureSettings.data.SettingsKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed class AllSettings(val key: SettingsKey, val category: SettingsCategory) {
    @Serializable
    sealed interface SettingValue

    @Serializable
    data class Theme(val theme: ThemeValue = ThemeValue.SYSTEM) :
        AllSettings(SettingsKey.THEME, SettingsCategory.MAIN) {
        @Serializable
        sealed class ThemeValue : SettingValue {
            @Serializable
            data object DARK : ThemeValue()

            @Serializable
            data object LIGHT : ThemeValue()

            @Serializable
            data object SYSTEM : ThemeValue()
        }
    }

    @Serializable
    data class Language(val language: LanguageValue = LanguageValue.System) :
        AllSettings(SettingsKey.LANGUAGE, SettingsCategory.MAIN) {
        @Serializable
        sealed class LanguageValue(val languageString: String, @StringRes val uiString: Int) :
            SettingValue {
            @Serializable
            data object System : LanguageValue("system", R.string.language_system)

            @Serializable
            data object English : LanguageValue("en", R.string.language_en)

            @Serializable
            data object Latvian : LanguageValue("lv", R.string.language_lv)
        }
    }

    @Serializable
    data class TimeDiffFormat(val format: TimeDiffFormatValue = TimeDiffFormatValue.Seconds) :
        AllSettings(SettingsKey.TIME_DIFF_FORMAT, SettingsCategory.MAIN) {
        @Serializable
        sealed class TimeDiffFormatValue(@StringRes val uiString: Int) : SettingValue {
            @Serializable
            data object Seconds : TimeDiffFormatValue(R.string.seconds)

            @Serializable
            data object Minutes : TimeDiffFormatValue(R.string.minutes)
        }
    }
}

/**
 * Gets serializer based on key
 * @param key Key of setting. Should use [getKey] for it
 */
private fun <T : AllSettings> getSerializer(key: SettingsKey): KSerializer<T> {
    @Suppress("UNCHECKED_CAST")
    return when (key) {
        SettingsKey.THEME -> AllSettings.Theme.serializer()
        SettingsKey.LANGUAGE -> AllSettings.Language.serializer()
        SettingsKey.TIME_DIFF_FORMAT -> AllSettings.TimeDiffFormat.serializer()
    } as KSerializer<T>
}

/**
 * Get key of setting
 * @param setting [AllSettings] - can use default constructor
 */
fun getKey(setting: AllSettings): SettingsKey {
    return when (setting) {
        is AllSettings.Theme -> SettingsKey.THEME
        is AllSettings.Language -> SettingsKey.LANGUAGE
        is AllSettings.TimeDiffFormat -> SettingsKey.TIME_DIFF_FORMAT
    }
}

/**
 * @param T setting's output type
 */
fun <T : AllSettings> Settings.toAllSettings(): T {
    return Json.decodeFromString(getSerializer(this.key), this.value_)
}

/**
 * Convert from [AllSettings] to [Settings]
 */
fun AllSettings.toSettings(): Settings {
    return Settings(
        this.key,
        this.category,
        Json.encodeToString(getSerializer(this.key), this)
    )
}