package com.example.quhu.ui.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkdomain.model.CoinsResponse
import com.example.quhu.R
import com.example.quhu.base.BaseActivity
import com.example.quhu.base.ViewState
import com.example.quhu.databinding.ActivityMainBinding
import com.example.quhu.ui.main.adapter.MainAdapter
import com.example.quhu.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main
    private var list = ArrayList<CoinsResponse>()


    private val _viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        addListener()
        addObservers()
    }


    private fun addListener() {
        with(binding) {
            appbar.imgSearch.setOnClickListener {
                appbar.clNormalLayout.visibility = View.GONE
                appbar.clSearch.visibility = View.VISIBLE
            }
            binding.appbar.imgNormal.setOnClickListener {
                binding.appbar.clNormalLayout.visibility = View.VISIBLE
                binding.appbar.clSearch.visibility = View.GONE
                (binding.rvList.adapter as MainAdapter).restoreAllList()
                this@MainActivity.hideKeyboard(appbar.imgNormal)
            }
            binding.appbar.imgCut.setOnClickListener {
                if (binding.appbar.etSearch.text.isNullOrBlank()) {
                    binding.appbar.clNormalLayout.visibility = View.VISIBLE
                    binding.appbar.clSearch.visibility = View.GONE
                    (binding.rvList.adapter as MainAdapter).restoreAllList()
                    this@MainActivity.hideKeyboard(appbar.imgCut)
                    return@setOnClickListener
                }
                binding.appbar.etSearch.text?.clear()
            }
            binding.appbar.etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    (binding.rvList.adapter as MainAdapter).filter.filter(p0.toString());
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

        }

    }

    private fun initUi() {
        with(binding) {
            appbar.tvTitle.text = getString(R.string.text_qoinhu)
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = MainAdapter()
            _viewModel.getAllList()
        }

    }


    override fun addObservers() {
        observeList()
        observeViewState()
    }


    private fun observeViewState() {
        _viewModel.viewState.observe(this) {
            when (it) {
                ViewState.Loading -> {
                    uiUtil.showProgress()
                }
                ViewState.Success() -> {
                    uiUtil.hideProgress()
                }
                else -> {
                    uiUtil.hideProgress()
                }
            }
        }
    }


    private fun observeList() {
        _viewModel.allCoins.observe(this) {
            if (it.isNullOrEmpty()) {
                showMessage("No List Found")
                return@observe
            }
            list.clear()
            list.addAll(it)
            (binding.rvList.adapter as MainAdapter).submitList(it as ArrayList<CoinsResponse>)
        }
    }

    override fun onDestroy() {
        list.clear()
        super.onDestroy()
    }


}





