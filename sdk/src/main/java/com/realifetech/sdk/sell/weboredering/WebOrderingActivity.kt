package com.realifetech.sdk.sell.weboredering

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.realifetech_sdk.R.id
import com.realifetech.realifetech_sdk.databinding.ActivityWebOrderingBinding

class WebOrderingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebOrderingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebOrderingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                id.main_container, WebOrderingFragment.newInstance()
            ).commit()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}