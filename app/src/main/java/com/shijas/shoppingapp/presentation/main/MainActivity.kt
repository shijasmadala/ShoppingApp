package com.shijas.shoppingapp.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.shijas.shoppingapp.databinding.ActivityMainBinding
import com.shijas.shoppingapp.presentation.main.adapters.BannerAdapter
import com.shijas.shoppingapp.presentation.main.adapters.CategoryAdapter
import com.shijas.shoppingapp.presentation.main.adapters.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val bannerAdapter by lazy { BannerAdapter() }
    private val productAdapter by lazy { ProductAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerViewCategory.adapter = categoryAdapter
        binding.recyclerViewBanner.adapter = bannerAdapter
        binding.recyclerViewProducts.adapter = productAdapter
        observeUi()
    }

    private fun observeUi() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainUiState.collectLatest { mainUiSate ->
                    handleLoading(mainUiSate.loading)
                    categoryAdapter.submitList(mainUiSate.categories)
                    bannerAdapter.submitList(mainUiSate.banners)
                    productAdapter.submitList(mainUiSate.products)
                    handleError(mainUiSate.error)
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleError(message : String?){
        message?.let {
            handleLoading(false)
            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        }
    }
}