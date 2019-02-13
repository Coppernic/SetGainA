package fr.coppernic.fixgaina.home

import android.annotation.SuppressLint
import fr.coppernic.fixgaina.rfid.RfidInteractor
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenterImpl @Inject constructor():HomePresenter {
    private lateinit var view:HomeView

    @Inject
    lateinit var rfidInteractor:RfidInteractor

    override fun setUp(view: HomeView):Completable {
        this.view = view

        this.view.enableButton(false)

        this.view.fillSpinner(rfidInteractor.getGainAValues())

        return rfidInteractor.setUp().subscribeOn(Schedulers.io())
                .andThen(Completable.defer { rfidInteractor.power(true) })
                .andThen(Completable.defer { rfidInteractor.open() })
    }

    @SuppressLint("CheckResult")
    override fun dispose() {
        rfidInteractor.close()
        rfidInteractor.power(false).subscribeOn(Schedulers.io())
                .subscribe {

                }
    }

    override fun setGainA() {
        view.showResult(rfidInteractor.setGainA(view.getGainA()))
    }
}