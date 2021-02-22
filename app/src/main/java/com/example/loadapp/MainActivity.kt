package com.example.loadapp

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.net.UrlQuerySanitizer
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager

    private var selection = "Glide - Image Loading Library by BumpTech"
    private var URL = "https://github.com/bumptech/glide"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

        create_channel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            download()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (context != null) {
                if (notificationManager == null) {
                    notificationManager = ContextCompat.getSystemService(
                        context,
                        NotificationManager::class.java
                    ) as NotificationManager
                }
                custom_button.finishDownload()
                notificationManager.sendNotification(selection, context)
            }
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
        custom_button.startDownload()
    }

    private fun create_channel(channelId: String, channelName: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = getColor(R.color.colorPrimary)
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_description)

            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun selectDownloadSite(view: View){
        when (view.id){
            R.id.udacity_radio -> {URL = getString(R.string.udacity_url)
                selection = getString(R.string.udacity_label)}
            R.id.retrofit_radio -> {URL = getString(R.string.retrofit_url)
                selection = getString(R.string.retrofit_label)}
            else -> {URL = getString(R.string.glide_url)
               selection = getString(R.string.glide_label)}
        }
    }


}