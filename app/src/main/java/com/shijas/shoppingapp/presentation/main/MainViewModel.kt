package com.shijas.shoppingapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shijas.shoppingapp.app.util.Resource
import com.shijas.shoppingapp.domian.model.Banner
import com.shijas.shoppingapp.domian.model.Category
import com.shijas.shoppingapp.domian.model.Product
import com.shijas.shoppingapp.domian.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

    init {
        getHomeScreenItems()
    }

    private fun getHomeScreenItems() {
        viewModelScope.launch {
            homeRepository.getHomeScreenItems().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _mainUiState.update { it.copy(loading = true) }
                    }
                    is Resource.Success -> {
                        _mainUiState.update { it.copy(loading = false) }
                        handleCategory(result.value.first)
                        handleBanners(result.value.second)
                        handleProducts(result.value.third)
                    }
                    is Resource.Error -> {
                        _mainUiState.update { it.copy(loading = false, error = result.error) }
                    }
                }
            }
        }
    }

    private  fun handleCategory(categoriesFlow: Flow<List<Category>>) {
        viewModelScope.launch {
            categoriesFlow.collect { categories ->
                _mainUiState.update { it.copy(categories = categories) }
            }
        }
    }

    private  fun handleBanners(bannersFlow: Flow<List<Banner>>) {
        viewModelScope.launch {
            bannersFlow.collect { banners ->
                _mainUiState.update { it.copy(banners = banners) }
            }
        }
    }

    private  fun handleProducts(productsFlow: Flow<List<Product>>) {
        viewModelScope.launch {
            productsFlow.collect { products ->
                _mainUiState.update { it.copy(products = products) }
            }
        }
    }
}