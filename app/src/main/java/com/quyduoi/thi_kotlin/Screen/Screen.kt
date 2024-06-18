package com.quyduoi.thi_kotlin.Screen

sealed class Screens (val screen: String){
    data object ManChao : Screens("ManChao")
    data object TrangChu : Screens("TrangChu")
    data object ManThem : Screens("ManThem")
    data object ManCapNhat : Screens("ManCapNhat")
}