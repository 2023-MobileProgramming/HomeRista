package com.coffee.homerista.BeanSlide;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coffee.homerista.repository.BeanRepository

class BeanSlideViewModelFactory(private val repository: BeanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeanSlideViewModel::class.java)) {
            return BeanSlideViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}