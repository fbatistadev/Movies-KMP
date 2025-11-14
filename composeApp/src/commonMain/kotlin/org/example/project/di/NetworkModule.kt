package org.example.project.di

import org.example.project.data.network.KtorApiClient
import org.koin.dsl.module

val networkModule = module {
    single { KtorApiClient() }
}