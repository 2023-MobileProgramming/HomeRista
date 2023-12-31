package com.coffee.homerista.ui.viewmoel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.coffee.homerista.MyApplication
import com.coffee.homerista.data.entities.Record
import com.coffee.homerista.repository.RecordRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class RecordViewModel(
    private val repository: RecordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataList = MutableLiveData<List<Record>>()
    private val _dataByDateList = MutableLiveData<List<Record>>()
    private val _dataByMonthList = MutableLiveData<List<Record>>()
    val dataList: LiveData<List<Record>> get() = _dataList
    val dataByDateList: LiveData<List<Record>> get() = _dataByDateList
    val dataByMonthList: LiveData<List<Record>> get() = _dataByMonthList

    init {
        Log.d(TAG, "RecordViewModel - 생성자 호출")
        loadData()
        loadDataByDate(LocalDate.now())
    }

    private fun loadData() {
        viewModelScope.launch {
            _dataList.value = repository.getAll()
        }
        Log.d("loadData", "데이터 로드 : ${_dataList.value?.size},  ${_dataList.value}")
    }

    fun loadDataByDate(date: LocalDate) {
        viewModelScope.launch {
            _dataByDateList.value = repository.getAllByDate(date)
        }
    }

    fun loadDataByMonth(date: LocalDate) {
        viewModelScope.launch {
            _dataByMonthList.value = repository.getAllByMonth(date)
        }
    }
    // 데이터 추가
    fun insert(record: Record) {
        viewModelScope.launch {
            repository.insert(record)
            loadData() // 데이터가 추가되었으므로 다시 데이터를 로드
            loadDataByDate(record.date)
        }
    }

    // 데이터 수정
    fun update(record: Record) {
        viewModelScope.launch {
            repository.update(record)
            loadData() // 데이터가 수정되었으므로 다시 데이터를 로드
            loadDataByDate(record.date)
        }
    }

    // 데이터 삭제
    fun delete(record: Record) {
        viewModelScope.launch {
            repository.delete(record)
            loadData() // 데이터가 삭제되었으므로 다시 데이터를 로드
            loadDataByDate(record.date)
        }
    }

    // Define ViewModel factory in a companion object
    companion object {
        const val TAG: String = "로그"
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).recordRepository
                RecordViewModel(
                    repository = myRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}
