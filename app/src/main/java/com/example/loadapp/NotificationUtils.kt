package com.example.loadapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.security.AccessControlContext

private val NOTIFICATION_ID = 1212

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context){
    val resultIntent = Intent(applicationContext, DetailActivity::class.java)
    resultIntent.putExtra("download", messageBody)

    val resultPendingIntent = TaskStackBuilder.create(applicationContext).run {
        addNextIntentWithParentStack(resultIntent)
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.notification_channel_id))
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentIntent(resultPendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}