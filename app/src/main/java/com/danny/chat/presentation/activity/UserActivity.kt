package com.danny.chat.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.danny.chat.data.utils.Transaction
import com.danny.chat.databinding.ActivityUserBinding
import com.danny.chat.presentation.adapter.RoomAdapter
import com.danny.chat.presentation.viewmodel.user.UserViewModel
import com.danny.chat.presentation.viewmodel.user.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserBinding

    @Inject
    lateinit var factory: UserViewModelFactory

    @Inject
    lateinit var roomAdapter: RoomAdapter

    @Inject
    lateinit var options: GoogleSignInOptions

    private val viewModel: UserViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.roomRecycler.adapter = roomAdapter
        binding.roomRecycler.layoutManager = LinearLayoutManager(this)

        roomAdapter.setOnItemClick {
            println(it)
            val intent = Intent(this@UserActivity, ChatActivity::class.java)
            intent.putExtra("room", it)
            startActivity(intent)
        }

        with(viewModel) {
            getAllRooms(getUser().uid).observe(this@UserActivity) {
                roomAdapter.list.submitList(it)
            }
            transaction.observe(this@UserActivity) {
                it.getContentIfNotHandled()?.let { value ->
                    when (value) {
                        Transaction.MAIN -> {
                            startActivity(
                                Intent(
                                    this@UserActivity,
                                    MainActivity::class.java
                                ).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                },
                            )
                        }
                    }
                }
            }
        }
        binding.fab.setOnClickListener {
            viewModel.signOut(GoogleSignIn.getClient(this, options))
            /*viewModel.addRoom(
                Room(
                    null,
                    listOf(viewModel.getUser().uid),
                    "ASdf",
                    "TestLM",
                    Calendar.getInstance().timeInMillis
                )
            )*/
        }
    }
}