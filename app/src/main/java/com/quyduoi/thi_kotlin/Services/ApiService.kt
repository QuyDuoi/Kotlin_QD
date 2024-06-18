package com.quyduoi.thi_kotlin.Services

import com.quyduoi.thi_kotlin.Model.SinhVien
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("them")
    suspend fun them(@Body sanPham: SinhVien): Response<SinhVien>

    @PUT("sua/{id}")
    suspend fun sua(@Path("id") id: String, @Body sanPham: SinhVien): Response<SinhVien>

    @DELETE("xoa/{id}")
    suspend fun xoa(@Path("id") id: String): Response<SinhVien>

    @GET("layDanhSach")
    suspend fun layDanhSach(): Response<List<SinhVien>>
}