package com.quyduoi.thi_kotlin.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.quyduoi.thi_kotlin.Model.SanPham
import com.quyduoi.thi_kotlin.Model.XeHoi
import com.quyduoi.thi_kotlin.Services.Repository
import kotlinx.coroutines.launch

class SanPhamViewModel : ViewModel() {

    private val repository = Repository()

    private val _sanPhams = MutableLiveData<List<XeHoi>>()
    val sanPhams: LiveData<List<XeHoi>> = _sanPhams

    init {
        layDanhSach()
    }

    private fun layDanhSach() {
        viewModelScope.launch {
            val result = repository.layDanhSach()
            _sanPhams.postValue(result ?: emptyList())
        }
    }

    fun refreshList() {
        viewModelScope.launch {
            val result = repository.layDanhSach()
            _sanPhams.postValue(result ?: emptyList())
        }
    }

    fun them(sanPham: XeHoi) {
        viewModelScope.launch {
            val result = repository.them(sanPham)
            if (result != null) {
                val updatedList = _sanPhams.value?.toMutableList() ?: mutableListOf()
                updatedList.add(result)
                _sanPhams.value = updatedList
                layDanhSach()
            }
        }
    }

    fun sua(id: String, sanPham: XeHoi) {
        viewModelScope.launch {
            val result = repository.sua(id, sanPham)
            if (result != null) {
                val updatedList = _sanPhams.value?.toMutableList() ?: mutableListOf()
                val index = updatedList.indexOfFirst { it._id == id }
                if (index != -1) {
                    updatedList[index] = result
                    _sanPhams.value = updatedList
                    layDanhSach()
                }
            }
        }
    }

    fun xoa(id: String) {
        viewModelScope.launch {
            val result = repository.xoaLoaiMon(id)
            if (result != null) {
                val updatedList = _sanPhams.value?.toMutableList() ?: mutableListOf()
                updatedList.removeAll { it._id == id }
                _sanPhams.value = updatedList
                layDanhSach()
            }
        }
    }
}