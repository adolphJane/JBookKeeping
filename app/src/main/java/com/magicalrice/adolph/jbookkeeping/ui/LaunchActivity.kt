package com.magicalrice.adolph.jbookkeeping.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.magicalrice.adolph.jbookkeeping.ui.home.HomeActivity

/**
 * Created by Adolph on 2018/7/5.
 */
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
}