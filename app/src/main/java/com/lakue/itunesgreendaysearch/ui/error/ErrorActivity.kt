package com.lakue.itunesgreendaysearch.ui.error

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseActivity
import com.lakue.itunesgreendaysearch.databinding.ActivityErrorBinding
import com.lakue.itunesgreendaysearch.databinding.ActivityMainBinding
import com.lakue.itunesgreendaysearch.ui.main.MainViewModel

class ErrorActivity : BaseActivity<ActivityErrorBinding, ErrorViewModel>(R.layout.activity_error) {

    companion object {
        const val EXTRA_INTENT = "EXTRA_INTENT"
        const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"
    }

    private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>(EXTRA_INTENT) }
    private val errorText by lazy { intent.getStringExtra(EXTRA_ERROR_TEXT) }

    override fun init() {
        binding.tvErrorLog.text = errorText

        binding.btnReload.setOnClickListener {
            startActivity(lastActivityIntent)
            finish()
        }
    }
}