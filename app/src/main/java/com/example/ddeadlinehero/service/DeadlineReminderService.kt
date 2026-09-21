package com.example.ddeadlinehero.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class DeadlineReminderService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("DeadlineService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DeadlineService", "Service started")

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DeadlineService", "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
