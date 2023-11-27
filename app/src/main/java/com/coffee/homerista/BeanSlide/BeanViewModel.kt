package com.coffee.homerista.BeanSlide

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.coffee.homerista.MyApplication
import com.coffee.homerista.data.entities.Bean
import com.coffee.homerista.repository.BeanRepository
import kotlinx.coroutines.launch

class BeanViewModel(
    private val repository: BeanRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataList = MutableLiveData<List<Bean>>()
    val dataList: LiveData<List<Bean>> get() = _dataList

    init {
        Log.d(TAG, "BeanViewModel - 생성자 호출")
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _dataList.value = repository.getAll()
        }
        Log.d("loadData", "데이터 로드 : ${_dataList.value?.size},  ${_dataList.value}")
    }
    // 데이터 추가
    fun insert(bean: Bean) {
        viewModelScope.launch {
            repository.insert(bean)
            loadData() // 데이터가 추가되었으므로 다시 데이터를 로드
        }
    }

    // 데이터 수정
    fun update(bean: Bean) {
        viewModelScope.launch {
            repository.update(bean)
            loadData() // 데이터가 수정되었으므로 다시 데이터를 로드
        }
    }

    // 데이터 삭제
    fun delete(bean: Bean) {
        viewModelScope.launch {
            repository.delete(bean)
            loadData() // 데이터가 삭제되었으므로 다시 데이터를 로드
        }
    }

    // Define ViewModel factory in a companion object
    companion object {
        const val TAG: String = "로그"
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository = (this[APPLICATION_KEY] as MyApplication).beanRepository
                BeanViewModel(
                    repository = myRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}


