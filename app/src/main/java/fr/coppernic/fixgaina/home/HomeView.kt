package fr.coppernic.fixgaina.home

interface HomeView {
    fun fillSpinner(dbs:List<String>)
    fun getGainA():String
    fun showResult(result:Boolean)
    fun enableButton(enable:Boolean)
}