package com.danny.chat.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.danny.chat.data.utils.Transaction
import com.danny.chat.databinding.ActivityMainBinding
import com.danny.chat.presentation.viewmodel.main.MainViewModel
import com.danny.chat.presentation.viewmodel.main.MainViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "MAIN_ACTIVITY"
    }

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var factory: MainViewModelFactory

    @Inject
    lateinit var options: GoogleSignInOptions

    lateinit var client: GoogleSignInClient

    private val viewModel: MainViewModel by viewModels { factory }

    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val res = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            res.addOnCompleteListener { task ->
                if (task.isSuccessful) viewModel.loginUser(task.result!!)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = GoogleSignIn.getClient(this, options)

        binding.signIn.setOnClickListener {
            signIn()
        }
        viewModel.spinner.observe(this) {
            val spinnerVisibility = if (it) View.VISIBLE else View.INVISIBLE
            val signInVisibility = if (it) View.INVISIBLE else View.VISIBLE
            binding.spinner.visibility = spinnerVisibility
            binding.signIn.visibility = signInVisibility
        }
        viewModel.transaction.observe(this) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    Transaction.USER -> {
                        val intent = Intent(this@MainActivity, UserActivity::class.java)
                        startActivity(intent)
                    }
                    Transaction.NAME -> {
                        val intent = Intent(this@MainActivity, NameActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        Log.e(LOG_TAG, "Unexpected transaction ${it}")
                    }
                }
            }
        }
        viewModel.toast.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        signIn()
    }

    private fun signIn() {
        val intent = client.signInIntent;
        loginLauncher.launch(intent)
    }
}