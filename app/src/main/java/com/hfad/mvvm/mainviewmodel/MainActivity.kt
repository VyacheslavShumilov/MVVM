package com.hfad.mvvm.mainviewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hfad.mvvm.mainviewmodel.adapter.AdapterUsers
import com.hfad.mvvm.databinding.ActivityMainBinding
import com.hfad.mvvm.factory.MainModelFactory
import com.hfad.mvvm.repository.MainRepository
import com.hfad.mvvm.services.Api
import com.hfad.mvvm.userviewmodel.UserActivity

class MainActivity : AppCompatActivity(), AdapterUsers.SetOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var adapterUsers = AdapterUsers(this)
    private lateinit var api: Api
    private lateinit var mainRepository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        api = Api.getInstance()
        mainRepository = MainRepository(api)
        binding.recyclerView.adapter = adapterUsers

        //запрос на viewModel
        viewModel = ViewModelProvider(this, MainModelFactory(mainRepository)).get(MainViewModel::class.java)

        viewModel.userList.observe(this) { users ->
            adapterUsers.setUsers(users)
            binding.noInternetLinLayout.visibility = View.GONE
        }

        viewModel.loading.observe(this) { show ->
            if (show) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        //если нет подключения к интернету
        viewModel.errorMessage.observe(this) { message ->
            binding.noInternetLinLayout.visibility = View.VISIBLE
        }

        viewModel.getAllUsers()


        binding.btnClickRepeat.setOnClickListener {
            viewModel.getAllUsers()
        }
    }

    override fun onClickUser(login: String) {
        val intent = Intent(this, UserActivity::class.java)
        intent.putExtra("LOGIN", login)
        startActivity(intent)
    }
}