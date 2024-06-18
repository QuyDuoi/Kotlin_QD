package com.quyduoi.thi_kotlin.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.quyduoi.thi_kotlin.Model.SinhVien
import com.quyduoi.thi_kotlin.ViewModel.SanPhamViewModel
import kotlinx.coroutines.delay

@Composable
fun ManCapNhat(navController: NavHostController,viewModel: SanPhamViewModel, sanPham: SinhVien) {
    var idSinhVien by remember { mutableStateOf(sanPham._id) }
    var PH41939_mssv by remember { mutableStateOf(sanPham.PH41939_mssv) }
    var PH41939_diemTB by remember { mutableStateOf(sanPham.PH41939_diemTB.toString()) }
    var PH41939_tensv by remember { mutableStateOf(sanPham.PH41939_tensv) }
    val isChecked = remember { mutableStateOf(sanPham.PH41939_trangthai) }
    Box(modifier = Modifier.fillMaxSize(),
        Alignment.Center) {
        var showThongBao by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }
        var showToastAndNavigate by remember { mutableStateOf(false) }

        val context = LocalContext.current

        if (showToastAndNavigate) {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show()
                delay(1000)
                navController.navigate(Screens.TrangChu.screen) {
                    popUpTo(Screens.ManCapNhat.screen) { inclusive = true }
                }
                showToastAndNavigate = false
            }
        }

        if (showThongBao) {
            DialogComponent(
                onConfirmation = { showThongBao = false },
                dialogTitle = "Thông báo",
                dialogMessage = dialogMessage
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEBE9E9))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cập nhật thông tin sinh viên",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                OutlinedTextField(
                    value = PH41939_mssv,
                    onValueChange = { PH41939_mssv = it },
                    label = { Text(text = "Mã sinh viên") },
                    placeholder = { Text(text = "Nhập mã sinh viên") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 10.dp, top = 25.dp),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = PH41939_diemTB,
                    onValueChange = { PH41939_diemTB = it },
                    label = { Text(text = "Điểm trung bình") },
                    placeholder = { Text(text = "Nhập điểm trung bình") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 10.dp, top = 25.dp),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = PH41939_tensv,
                    onValueChange = { PH41939_tensv = it },
                    label = { Text(text = "Tên sinh viên") },
                    placeholder = { Text(text = "Nhập tên sinh viên") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 10.dp, top = 25.dp),
                    maxLines = 1
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 10.dp, top = 25.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Trạng thái: ")
                    Row(Modifier.fillMaxWidth()) {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                isChecked.value = it
                            }
                        )
                        Text("Đã tốt nghiệp", modifier = Modifier.padding(start = 8.dp, top = 15.dp))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navController.navigate(Screens.TrangChu.screen) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFB703),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Quay lại")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val priceDouble = PH41939_diemTB.toDoubleOrNull()
                            if (PH41939_mssv.trim().isEmpty() || PH41939_diemTB.trim().isEmpty() || PH41939_tensv.trim().isEmpty()) {
                                dialogMessage = "Vui lòng điền đầy đủ các trường thông tin!"
                                showThongBao = true
                            } else if (priceDouble == null) {
                                dialogMessage = "Điểm phải là số! ${priceDouble}"
                                showThongBao = true
                            } else if (priceDouble < 0 || priceDouble > 10) {
                                dialogMessage = "Điểm phải lớn hơn 0 và nhỏ hơn 10"
                                showThongBao = true
                            } else {
                                val newSinhVien = SinhVien("", PH41939_mssv, priceDouble, PH41939_tensv, isChecked.value)
                                val result = viewModel.sua(idSinhVien, newSinhVien)
                                if (result != null) {
                                    viewModel.refreshList()
                                    showToastAndNavigate = true
                                } else {
                                    Toast.makeText(context, "Mã sinh viên đã tồn tại!", Toast.LENGTH_SHORT)
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFB703),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Xác nhận")
                    }
                }
            }
        }
    }
}