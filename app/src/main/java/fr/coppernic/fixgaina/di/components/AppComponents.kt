package fr.coppernic.fixgaina.di.components

import dagger.Component
import fr.coppernic.fixgaina.di.modules.ContextModule
import fr.coppernic.fixgaina.di.modules.PresenterModule
import fr.coppernic.fixgaina.home.HomeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, PresenterModule::class])
interface AppComponents {

    fun inject(homeActivity: HomeActivity)
}
