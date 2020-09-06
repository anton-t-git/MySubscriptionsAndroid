package com.example.mysubs

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import db.AppDatabase
import db.Subscription
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val PREFS_NAME = "MyPrefsFile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        if (checkFirstRun()) { }
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-name"
        ).build()

        db.subscriptionDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isEmpty()){
                    supportActionBar?.setDisplayShowTitleEnabled(false)

                    val mainPlaceholder = findViewById<TextView>(R.id.mainPlaceholder)
                    mainPlaceholder.text = getString(R.string.main_text_placeholder)
                } else {
                    for (sub in it){
                        print(sub)
                    }
                }
            },{})
            .let { compositeDisposable.add(it) }
//        if (subscriptions.isEmpty()) {
//            supportActionBar?.setDisplayShowTitleEnabled(false)
//
//            val mainPlaceholder = findViewById<TextView>(R.id.mainPlaceholder)
//            mainPlaceholder.text = getString(R.string.main_text_placeholder)
//        }

        val textConfirmRequest = getString(R.string.add_subscription_confirm_request)
        val textConfirmPositive = getString(R.string.add_subscription_confirm_positive)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, textConfirmRequest, Snackbar.LENGTH_LONG)
                    .setAction(textConfirmPositive, View.OnClickListener { }).show()
        }
    }

    private fun checkFirstRun(): Boolean{
        val settings = getSharedPreferences(PREFS_NAME, 0)

        val optionKey = "my_first_time"
        val isFirstRun = settings.getBoolean(optionKey, true)
        if (!isFirstRun) {
            settings.edit().putBoolean(optionKey, false).apply()
        }
        return isFirstRun
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}