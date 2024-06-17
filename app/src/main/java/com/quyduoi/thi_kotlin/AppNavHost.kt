package com.quyduoi.thi_kotlin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quyduoi.thi_kotlin.Screen.ManChao
import com.quyduoi.thi_kotlin.Screen.Screens
import com.quyduoi.thi_kotlin.Screen.TrangChu
import com.quyduoi.thi_kotlin.ViewModel.SanPhamViewModel

@Composable
fun AppNavHost (navController : NavHostController, viewModel: SanPhamViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.ManChao.screen,
    ) {
        composable(Screens.ManChao.screen) {
            ManChao(navController)
        }
        composable(Screens.TrangChu.screen) {
            TrangChu(viewModel)
        }
    }
}
