package com.example.loadapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        var intent = intent
        if (intent != null && intent.hasExtra("download")){
                val downloadFile = intent.getStringExtra("download")
            if (downloadFile != null){
                file_name_display.setText(downloadFile)
                download_status_display.setText(getString(R.string.success))
            } else {
                download_status_display.setText(getString(R.string.failure))
            }
            }
    }

}