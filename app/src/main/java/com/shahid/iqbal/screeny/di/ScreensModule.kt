package com.shahid.iqbal.screeny.di

import com.shahid.iqbal.screeny.ui.screens.home.WallpaperViewModel
import com.shahid.iqbal.screeny.ui.screens.category.CategoryViewModel
import com.shahid.iqbal.screeny.ui.screens.favourite.FavouriteViewModel
import com.shahid.iqbal.screeny.ui.screens.search.SearchViewModel
import com.shahid.iqbal.screeny.ui.screens.settings.core.SettingViewModel
import com.shahid.iqbal.screeny.ui.screens.settings.language.LanguageViewModel
import com.shahid.iqbal.screeny.ui.screens.splash.SplashViewModel
import com.shahid.iqbal.screeny.ui.screens.wallpapers.ActionViewModel
import com.shahid.iqbal.screeny.ui.shared.SharedWallpaperViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val screensModule = module {

    viewModel { SplashViewModel() }
    viewModel { WallpaperViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { SharedWallpaperViewModel() }
    viewModel { ActionViewModel(get(),get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel{LanguageViewModel(get())}
    viewModel { SettingViewModel(get()) }


}