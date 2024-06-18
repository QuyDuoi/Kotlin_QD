package com.quyduoi.thi_kotlin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quyduoi.thi_kotlin.Screen.ManCapNhat
import com.quyduoi.thi_kotlin.Screen.ManChao
import com.quyduoi.thi_kotlin.Screen.ManThem
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
            TrangChu(viewModel, navController)
        }
//        composable(Screens.ManThem.screen) {
//            ManThem(navController, viewModel)
//        }
//        composable("${Screens.ManCapNhat.screen}/{sanPhamId}") { backStackEntry ->
//            val dishId = backStackEntry.arguments?.getString("sanPhamId")
//            val dish = viewModel.sanPhams.value?.find { it._id == dishId }
//            dish?.let {
//                ManCapNhat(navController = navController, viewModel, sanPham = it)
//            }
//        }
    }
}
