package com.dehaat.goodreads.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dehaat.goodreads.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}