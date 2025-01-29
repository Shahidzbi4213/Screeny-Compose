package com.shahid.iqbal.screeny.ui.screens.settings.utils

import com.shahid.iqbal.screeny.ui.screens.settings.utils.CountryFlags.getCountryFlag



val LANGUAGES_LIST by lazy {
    listOf(
        LanguageEntity("sa", "Arabic", "ar", getCountryFlag("sa")),
        LanguageEntity("gb", "English", "en", getCountryFlag("gb")),
        LanguageEntity("ru", "Russian", "ru", getCountryFlag("ru")),
        LanguageEntity("id", "Indonesian", "in", getCountryFlag("id")),
        LanguageEntity("bd", "Bengali", "bn", getCountryFlag("bd")),
        LanguageEntity("in", "Hindi", "hi", getCountryFlag("in")),
        LanguageEntity("kp", "Korean", "ko", getCountryFlag("kp")),
        LanguageEntity("jp", "Japanese", "ja", getCountryFlag("jp")),
        LanguageEntity("cn", "Chinese", "zh", getCountryFlag("cn")),
        LanguageEntity("pl", "Polish", "pl", getCountryFlag("pl")),
        LanguageEntity("fr", "French", "fr", getCountryFlag("fr")),
        LanguageEntity("it", "Italian", "it", getCountryFlag("it")),
        LanguageEntity("in", "Tamil", "ta", getCountryFlag("in")),
        LanguageEntity("pk", "Urdu", "ur", getCountryFlag("pk")),
        LanguageEntity("de", "German", "de", getCountryFlag("de")),
        LanguageEntity("es", "Spanish", "es", getCountryFlag("es")),
        LanguageEntity("nl", "Dutch", "nl", getCountryFlag("nl")),
        LanguageEntity("pt", "Portuguese", "pt", getCountryFlag("pt")),
        LanguageEntity("th", "Thai", "th", getCountryFlag("th")),
        LanguageEntity("tr", "Turkish", "tr", getCountryFlag("tr")),
        LanguageEntity("ro", "Romanian", "ro", getCountryFlag("ro")),
        LanguageEntity("ms", "Malay", "ro", getCountryFlag("ms")),
        LanguageEntity("ir", "Persian", "fa", getCountryFlag("ir"))
    ).sortedBy { it.languageName }
}
