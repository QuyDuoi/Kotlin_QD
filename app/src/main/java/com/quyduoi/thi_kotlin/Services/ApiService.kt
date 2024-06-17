package com.quyduoi.thi_kotlin.Services

import com.quyduoi.thi_kotlin.Model.XeHoi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("them")
    suspend fun them(@Body sanPham: XeHoi): Response<XeHoi>

    @PUT("sua/{id}")
    suspend fun sua(@Path("id") id: String, @Body sanPham: XeHoi): Response<XeHoi>

    @DELETE("xoa/{id}")
    suspend fun xoa(@Path("id") id: String): Response<XeHoi>

    @GET("layDanhSach")
    suspend fun layDanhSach(): Response<List<XeHoi>>
}