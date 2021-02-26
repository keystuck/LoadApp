package com.example.loadapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        var intent = intent
        if (intent != null && intent.hasExtra(getString(R.string.download_key))){
                val downloadFile = intent.getStringExtra(getString(R.string.download_key))
            if (downloadFile != null){
                file_name_display.setText(downloadFile)
                if (intent.getBooleanExtra(getString(R.string.success), false) == true){
                    download_status_display.setText(getString(R.string.success))
                } else {
                    download_status_display.setText(getString(R.string.failure))
                    download_status_display.setTextColor(getColor(R.color.colorFailure))
                }
            } else {
                download_status_display.setText(getString(R.string.failure))
                download_status_display.setTextColor(getColor(R.color.colorFailure))
            }
            }
    }

    fun onBackPressed(view: View) {
        onBackPressed()
    }


}