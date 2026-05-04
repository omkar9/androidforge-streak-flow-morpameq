package com.androidforge.streakflow.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {
    // No specific presentation-level dependencies provided here for now.
    // ViewModels are typically injected directly using @HiltViewModel.
}