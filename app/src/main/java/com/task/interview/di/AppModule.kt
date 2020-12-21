package com.task.interview.di

import com.task.interview.App
import com.task.interview.data.local.db.LocationsDatabase
import com.task.interview.data.local.prefs.AppPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

val appModule = module {
    single { AppPreferences(get()) }
    single { androidApplication() as App }
    single { LocationsDatabase.getInstance(get()) }
}



inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy { GlobalContext.get().koin.get<T>(qualifier, parameters) }
