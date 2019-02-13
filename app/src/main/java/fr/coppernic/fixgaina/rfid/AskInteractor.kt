package fr.coppernic.fixgaina.rfid

import android.content.Context
import fr.coppernic.sdk.ask.Defines
import fr.coppernic.sdk.ask.Reader
import fr.coppernic.sdk.ask.Reader.getInstance
import fr.coppernic.sdk.power.impl.cone.ConePeripheral
import fr.coppernic.sdk.utils.core.CpcDefinitions
import fr.coppernic.sdk.utils.io.InstanceListener
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import javax.inject.Inject

class AskInteractor @Inject constructor():RfidInteractor {
    @Inject
    lateinit var context:Context

    private lateinit var instanceEmitter:CompletableEmitter
    private var reader: Reader? = null

    override fun setUp(): Completable {

        if (reader != null) {
            return Completable.complete()
        }

        getInstance(context, object:InstanceListener<Reader> {
            override fun onDisposed(p0: Reader) {

            }

            override fun onCreated(p0: Reader) {
                reader = p0
                instanceEmitter.onComplete()
            }
        })

        return Completable.create {
               instanceEmitter = it
        }
    }

    override fun power(on: Boolean): Completable {
        return Completable.fromSingle(ConePeripheral.RFID_ASK_UCM108_GPIO.descriptor.power(context, on))
    }

    override fun open(): Completable {
        reader.let{
            if (it == null) {
                return Completable.error(Throwable("reader is null"))
            }

            var res = it.cscOpen(CpcDefinitions.ASK_READER_PORT, 115200, false)

            if (res != Defines.RCSC_Ok) {
                return Completable.error(Throwable("open failed"))
            }

            val sb = StringBuilder()

            res = it.cscVersionCsc(sb)

            return if (res != Defines.RCSC_Ok || !sb.toString().contains("GEN5XX")) {
                Completable.error(Throwable("get version failed"))
            } else {
                Completable.complete()
            }
        }
    }

    override fun close() {
        reader.let{
            it?.cscClose()
        }
    }

    override fun getGainAValues(): List<String> {
        val dbValues = mutableListOf<String>()

        for (t in Defines.CscMifareNxpGain.values()) {
            dbValues.add(t.toString())
        }

        return dbValues
    }

    override fun setGainA(value: String):Boolean {
        reader.let {
            return it?.mifareNxpSetGainA(Defines.CscMifareNxpGain.valueOf(value)) == Defines.RCSC_Ok
        }
    }
}