package com.coffee.homerista.BeanSlide

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.homerista.data.entities.Bean
import com.coffee.homerista.repository.BeanRepository
import kotlinx.coroutines.launch

class BeanSlideViewModel(private val repository: BeanRepository) : ViewModel() {
    private val _dataList = MutableLiveData<List<Bean>>()
    val dataList: LiveData<List<Bean>> get() = _dataList

    init {
        Log.d(TAG, "BeanViewMOdel - 생성자 호출")
        viewModelScope.launch {
            _dataList.value = repository.getAll()
        }
    }

    // Define ViewModel factory in a companion object
    companion object {
        const val TAG: String = "로그"
    }
}


