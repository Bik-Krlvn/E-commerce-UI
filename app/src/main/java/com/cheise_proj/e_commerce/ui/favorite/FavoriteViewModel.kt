package com.cheise_proj.e_commerce.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cheise_proj.e_commerce.data.db.entity.CartEntity
import com.cheise_proj.e_commerce.data.db.entity.ProductWithFavorite
import com.cheise_proj.e_commerce.data.repository.ICartRepository
import com.cheise_proj.e_commerce.data.repository.IFavoriteRepository
import com.cheise_proj.e_commerce.data.repository.IProductRepository
import com.cheise_proj.e_commerce.di.IODispatcher
import com.cheise_proj.e_commerce.extension.onError
import com.cheise_proj.e_commerce.extension.onSuccess
import com.cheise_proj.e_commerce.model.Category
import com.cheise_proj.e_commerce.ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: IFavoriteRepository,
    private val productRepository: IProductRepository,
    private val cartRepository: ICartRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) :
    BaseViewModel() {
    private val _favoriteProducts: MutableLiveData<List<ProductWithFavorite>> = MutableLiveData()
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    private val _viewStatus: MutableLiveData<Boolean> = MutableLiveData(true)

    var favoriteProducts: LiveData<List<ProductWithFavorite>> = _favoriteProducts
    val categories: LiveData<List<Category>> = _categories
    val getViewStatus: LiveData<Boolean> = _viewStatus



    fun removeFavorite(identifier: Int?) {
        viewModelScope.launch(dispatcher) {
            favoriteRepository.removeFavorite(identifier)
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch(dispatcher) {
            favoriteRepository.getProductFavorite().collect {
                _favoriteProducts.postValue(it)
                _loadingState.postValue(false)

            }
        }
    }


    fun loadCategories() {
        viewModelScope.launch(dispatcher) {
            productRepository.getCategories()
                .onSuccess { category ->
                    _categories.postValue(category)
                }
                .onError { error ->
                    Timber.i("error: $error")
                    _loadingState.postValue(false)
                    _errorMsg.postValue(error.message)
                }
        }
    }

    fun addToCart(cartEntity: CartEntity) {
        viewModelScope.launch(dispatcher) {
            cartRepository.addToCart(cartEntity)
        }
    }

    fun setViewStatus(status: Boolean) {
        _viewStatus.value = status
    }
}