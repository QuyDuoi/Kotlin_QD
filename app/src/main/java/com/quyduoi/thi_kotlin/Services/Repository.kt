package com.quyduoi.thi_kotlin.Services

import android.util.Log
import com.quyduoi.thi_kotlin.Model.XeHoi

class Repository {
    private val apiService = RetrofitService().apiService

    suspend fun them (sanPham: XeHoi): XeHoi? {
        return try {
            val response = apiService.them(sanPham)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("Repository", "Thêm thất bại: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("Repository", "Ngoại lệ khi thêm ", e)
            null
        }
    }

    suspend fun sua(id: String, sanPham: XeHoi): XeHoi? {
        return try {
            val response = apiService.sua(id, sanPham)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("Repository", "Sửa thất bại: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("Repository", "Ngoại lệ khi sửa", e)
            null
        }
    }

    suspend fun xoaLoaiMon(id: String): XeHoi? {
        return try {
            val response = apiService.xoa(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("Repository", "Xóa thất bại: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("Repository", "Ngoại lệ khi xóa", e)
            null
        }
    }

    suspend fun layDanhSach(): List<XeHoi>? {
        return try {
            val response = apiService.layDanhSach()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("Repository", "Lấy danh sách thất bại: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("Repository", "Ngoại lệ khi lấy danh sách", e)
            null
        }
    }
}