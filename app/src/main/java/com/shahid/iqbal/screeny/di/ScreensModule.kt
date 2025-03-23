package com.shahid.iqbal.screeny.di

import com.shahid.iqbal.screeny.ui.screens.category.CategoryViewModel
import com.shahid.iqbal.screeny.ui.screens.favourite.FavouriteDetailScreenViewModel
import com.shahid.iqbal.screeny.ui.screens.favourite.FavouriteViewModel
import com.shahid.iqbal.screeny.ui.screens.home.WallpaperViewModel
import com.shahid.iqbal.screeny.ui.screens.search.SearchViewModel
import com.shahid.iqbal.screeny.ui.screens.settings.core.SettingViewModel
import com.shahid.iqbal.screeny.ui.screens.settings.language.LanguageViewModel
import com.shahid.iqbal.screeny.ui.screens.splash.SplashViewModel
import com.shahid.iqbal.screeny.ui.screens.wallpapers.ActionViewModel
import com.shahid.iqbal.screeny.ui.shared.SharedWallpaperViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val screensModule = module {


    viewModelOf(::SplashViewModel)
    viewModelOf(::WallpaperViewModel)
    viewModelOf(::WallpaperViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SharedWallpaperViewModel)
    viewModelOf(::ActionViewModel)
    viewModelOf(::FavouriteDetailScreenViewModel)
    viewModelOf(::FavouriteViewModel)
    viewModelOf(::LanguageViewModel)
    viewModelOf(::SettingViewModel)
}