package fr.coppernic.fixgaina.home

import io.reactivex.Completable

interface HomePresenter {
    fun setUp(view:HomeView):Completable
    fun setGainA()
    fun dispose()
}