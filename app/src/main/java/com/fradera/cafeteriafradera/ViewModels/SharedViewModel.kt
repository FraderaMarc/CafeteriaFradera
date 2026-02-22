package com.fradera.cafeteriafradera.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fradera.cafeteriafradera.data.ProducteCarrito

class SharedViewModel : ViewModel() {

    private val _items = MutableLiveData<MutableList<ProducteCarrito>>(mutableListOf())
    val items: LiveData<MutableList<ProducteCarrito>> get() = _items

    fun addItem(item: ProducteCarrito) {
        val list = _items.value ?: mutableListOf()
        list.add(item)
        _items.value = list
    }

    fun removeItem(item: ProducteCarrito) {
        val list = _items.value ?: mutableListOf()
        list.remove(item)
        _items.value = list
    }

    fun clearItems() {
        _items.value = mutableListOf()
    }

    fun getTotal(): Double =
        _items.value?.sumOf { it.preu } ?: 0.0
}