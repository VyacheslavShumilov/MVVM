package com.hfad.mvvm.userviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hfad.mvvm.databinding.ActivityUserBinding
import com.hfad.mvvm.factory.UserModelFactory
import com.hfad.mvvm.repository.UserRepository
import com.hfad.mvvm.services.Api
import com.squareup.picasso.Picasso

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var api: Api
    private lateinit var userRepository: UserRepository
    private lateinit var login: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.extras
        login = args?.get("LOGIN").toString()

        api = Api.getInstance()
        userRepository = UserRepository(api)

        viewModel = ViewModelProvider(this, UserModelFactory(userRepository)).get(UserViewModel::class.java)

        viewModel.getUser(login)

        viewModel.user.observe(this) { user ->

            binding.userLlInfo.visibility = View.VISIBLE
            binding.noInternetLinLayout.visibility = View.GONE
            Picasso.get().load(user.avatar_url).into(binding.avatarImageView)
            binding.userIdTextView.text = user.id.toString()
            binding.userLoginTextView.text = user.login
        }

        viewModel.loading.observe(this) { show ->
            if (show) {
                binding.noInternetLinLayout.visibility = View.GONE
                binding.userLlInfo.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this) {
            binding.noInternetLinLayout.visibility = View.VISIBLE
            binding.userLlInfo.visibility = View.GONE
        }


        binding.btnClickRepeat.setOnClickListener {
            viewModel.getUser(login)
        }
    }
}