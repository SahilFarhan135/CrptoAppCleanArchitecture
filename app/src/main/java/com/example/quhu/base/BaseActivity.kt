package com.example.quhu.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.quhu.util.UiUtil

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B
    protected lateinit var uiUtil: UiUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView(layoutId())
        uiUtil = UiUtil(this)
        addObservers()
    }


    private fun handleException(throwable: Throwable?) {
        showMessage(throwable?.message)
    }


    private fun bindContentView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun addObservers()

    protected fun showMessage(
        message: String?,
        button: Boolean = false,
        buttonText: String = "Ok"
    ) {
        message?.let { uiUtil.showMessage(it, button = button, buttonText = buttonText) }
    }

    protected fun showToast(
        message: String?,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        message?.let { uiUtil.showToast(it, duration) }
    }

    fun getLayoutBinding() = binding

}