package com.danny.chat.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.danny.chat.data.utils.Transaction
import com.danny.chat.databinding.ActivityNameBinding
import com.danny.chat.presentation.viewmodel.name.NameViewModel
import com.danny.chat.presentation.viewmodel.name.NameViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NameActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "NAME_ACTIVITY"
    }

    lateinit var binding: ActivityNameBinding

    @Inject
    lateinit var factory: NameViewModelFactory

    private val viewModel: NameViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.spinner.observe(this) {
            it.getContentIfNotHandled()?.let { show ->
                if (show) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.confirmButton.visibility = View.INVISIBLE
                } else {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.confirmButton.visibility = View.VISIBLE
                }
            }
        }

        viewModel.transaction.observe(this) {
            it.getContentIfNotHandled()?.let { transaction ->
                when (transaction) {
                    Transaction.USER -> {
                        val intent = Intent(this@NameActivity, UserActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        Log.e(LOG_TAG, "Unexpected transaction")
                    }
                }
            }
        }

        binding.confirmButton.setOnClickListener {
            viewModel.setName(binding.nameBox.text.toString())
        }
    }
}