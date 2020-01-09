package com.chernov.signalrtest

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.Stetho
import com.microsoft.signalr.HubConnection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var hubConnection: HubConnection

    lateinit var signalRListener: SignalRListener

    lateinit var numberFormat: NumberFormat

    private val number = 9999.55

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        numberFormat = NumberFormat.getCurrencyInstance(Locale.ITALY)
        Log.d("Currency Italy: ", numberFormat.format(number))

        numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("fr-FR"))
        Log.d("Currency France: ", numberFormat.format(number))

        numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("de-DE"))
        Log.d("Currency Germany: ", numberFormat.format(number))

        numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-US"))
        Log.d("Currency US: ", numberFormat.format(number))

        numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("ru-RU"))
        Log.d("Currency Russia: ", numberFormat.format(number))

        numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("nl-NL"))
        Log.d("Currency Holland: ", numberFormat.format(number))

        Stetho.initializeWithDefaults(this)
        setContentView(R.layout.activity_main)

        signalRListener = SignalRListener.getInstance()

        clickMeBtn.setOnClickListener {
            if (clickMeBtn.text.toString() == "start") {
                if (signalRListener.startConnection())
                    clickMeBtn.text = "stop"
            } else if (clickMeBtn.text.toString() == "stop") {
                if (signalRListener.stopConnection())
                    clickMeBtn.text = "start"
            }
        }
    }

    override fun onDestroy() {
        hubConnection.stop()
        super.onDestroy()
    }
}
