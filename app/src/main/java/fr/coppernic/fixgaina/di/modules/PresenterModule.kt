package fr.coppernic.fixgaina.di.modules

import dagger.Binds
import dagger.Module
import fr.coppernic.fixgaina.home.HomePresenter
import fr.coppernic.fixgaina.home.HomePresenterImpl
import fr.coppernic.fixgaina.rfid.AskInteractor
import fr.coppernic.fixgaina.rfid.RfidInteractor

@Module
interface PresenterModule {
    @Binds
    fun providesHomePresenter(presenter: HomePresenterImpl):HomePresenter

    @Binds
    fun providesRfidInteractor(rfidInteractor:AskInteractor):RfidInteractor
}