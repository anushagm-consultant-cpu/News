package com.example.newspulse.ui.listen

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.core.app.NotificationCompat
import com.example.newspulse.MainActivity
import com.example.newspulse.R
import java.util.Locale

class AudioReaderService : Service(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val binder = LocalBinder()
    private val CHANNEL_ID = "audio_reader_channel"
    private val NOTIFICATION_ID = 1

    private var currentTitle: String = "NewsPulse"
    private var currentSource: String = ""

    inner class LocalBinder : Binder() {
        fun getService(): AudioReaderService = this@AudioReaderService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        tts = TextToSpeech(this, this)
        createNotificationChannel()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.getDefault()
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    stopForeground(STOP_FOREGROUND_DETACH)
                }
                override fun onError(utteranceId: String?) {}
            })
        }
    }

    fun speak(text: String, title: String, source: String, speed: Float = 1.0f) {
        currentTitle = title
        currentSource = source
        
        tts?.setSpeechRate(speed)
        
        val params = android.os.Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "news_speech")
        
        startForeground(NOTIFICATION_ID, createNotification(title, source))
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "news_speech")
    }

    fun pause() {
        tts?.stop()
        stopForeground(STOP_FOREGROUND_DETACH)
    }

    fun stop() {
        tts?.stop()
        stopSelf()
    }

    fun setSpeed(speed: Float) {
        tts?.setSpeechRate(speed)
    }

    private fun createNotification(title: String, source: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(source)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "News Audio Reader",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Provides background audio for news articles"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
