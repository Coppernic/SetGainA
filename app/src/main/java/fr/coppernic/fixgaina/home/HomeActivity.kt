package fr.coppernic.fixgaina.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.coppernic.fixgaina.App
import fr.coppernic.fixgaina.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {
    @Inject
    lateinit var presenter:HomePresenter

    private lateinit var adapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.appComponents.inject(this)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf<String>())
        spDb.adapter = adapter
        floatingActionButton.setOnClickListener {
            presenter.setGainA()
        }
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()

        presenter.setUp(this).observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    enableButton(true)
                }, {
                    Snackbar.make(floatingActionButton, it.message.toString(), Snackbar.LENGTH_LONG).show()
                })
    }

    @SuppressLint("CheckResult")
    override fun onStop() {
        super.onStop()

        presenter.dispose()
    }

    override fun fillSpinner(dbs: List<String>) {
        adapter.clear()
        adapter.addAll(dbs)
        adapter.notifyDataSetChanged()
        spDb.setSelection(adapter.getPosition("DB_43"))
    }

    override fun getGainA(): String {
        return spDb.selectedItem.toString()
    }

    override fun showResult(result: Boolean) {
        var message = getString(R.string.result_error)
        when (result) {
            true -> message = getString(R.string.result_ok)
        }
        Snackbar.make(floatingActionButton, message, Snackbar.LENGTH_LONG).show()
    }

    override fun enableButton(enable: Boolean) {
        when (enable)  {
            true -> floatingActionButton.show()
            false -> floatingActionButton.hide()
        }
    }
}
