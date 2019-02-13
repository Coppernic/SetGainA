package fr.coppernic.fixgaina.rfid

import io.reactivex.Completable

interface RfidInteractor {
    fun setUp():Completable
    fun power(on:Boolean):Completable
    fun open():Completable
    fun close()
    fun getGainAValues():List<String>
    fun setGainA(value:String):Boolean
}