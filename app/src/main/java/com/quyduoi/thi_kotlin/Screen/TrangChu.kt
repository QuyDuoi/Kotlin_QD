package com.quyduoi.thi_kotlin.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.quyduoi.thi_kotlin.Model.SinhVien
import com.quyduoi.thi_kotlin.ViewModel.SanPhamViewModel

@Composable
fun TrangChu(viewModel: SanPhamViewModel, navController: NavHostController) {
    val sanPhamLists by viewModel.sanPhams.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var showDialogThem by remember { mutableStateOf(false) }
    var showDialogSua by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    var selectedSanPham by remember { mutableStateOf<SinhVien?>(null) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff252121)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Quản lý danh sách sinh viên", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                contentAlignment = Alignment.BottomEnd) {
                Button(onClick = {
                    showDialogThem = true
//                    navController.navigate(Screens.ManThem.screen)
                }) {
                    Text(text = "Thêm sinh viên")
                }
            }
            LazyColumn {
                itemsIndexed(sanPhamLists) { index, sanPham ->
                    SanPhamItem(sanPham, index + 1,
                        onEdit = {
                            selectedSanPham = sanPham
                            showDialogSua = true
//                            navController.navigate("${Screens.ManCapNhat.screen}/${sanPham._id}")
                        },
                        onDelete = {
                            selectedSanPham = sanPham
                            showDialog = true
                        },
                        onInfo = {
                            selectedSanPham = sanPham
                            showInfo = true
                        })
                    if (index < sanPhamLists.size - 1) {
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
        if (showDialog && selectedSanPham != null) {
            ConfirmDialogCard(
                title = "Xác nhận xóa",
                message = "Bạn có chắc chắn muốn xóa sinh viên có mã ${selectedSanPham?.PH41939_mssv}?",
                onConfirmClick = {
                    selectedSanPham?.let { sanPham ->
                        viewModel.xoa(sanPham._id)
                        Toast.makeText(context, "Xóa thông tin thành công!", Toast.LENGTH_SHORT).show()
                    }
                    showDialog = false
                    selectedSanPham = null
                },
                onDismissClick = {
                    showDialog = false
                    selectedSanPham = null
                }
            )
        }
        if (showDialogThem) {
            AddUpdateDialogCard(
                title = "Thêm thông tin sinh viên",
                onDismiss = { showDialogThem = false },
                onConfirm = { name, price, model, status ->
                    val newSanPham = SinhVien(
                        _id = "",
                        PH41939_mssv = name,
                        PH41939_diemTB = price,
                        PH41939_tensv = model,
                        PH41939_trangthai = status
                    )
                    viewModel.them(newSanPham)
                    Toast.makeText(context, "Thêm sản phẩm mới thành công!", Toast.LENGTH_SHORT).show()
                    showDialogThem = false
                }
            )
        }
        if (showDialogSua && selectedSanPham != null) {
            AddUpdateDialogCard(
                title = "Sửa thông tin",
                sanPham = selectedSanPham,
                onDismiss = { showDialogSua = false },
                onConfirm = { name, price, model, status ->
                    selectedSanPham?.let { sanPham ->
                        val updatedSanPham = sanPham.copy(
                            PH41939_mssv = name,
                            PH41939_diemTB = price,
                            PH41939_tensv = model,
                            PH41939_trangthai = status
                        )
                        viewModel.sua(sanPham._id, updatedSanPham)
                        Toast.makeText(context, "Cập nhât thông tin thành công!", Toast.LENGTH_SHORT).show()
                    }
                    showDialogSua = false
                    selectedSanPham = null
                }
            )
        }
        if (showInfo && selectedSanPham != null) {
            InformationDialogCard(
                title = "Thông tin sinh viên",
                sanPham = selectedSanPham!!,
                onClose = { showInfo = false }
            )
        }
    }
}

@Composable
fun SanPhamItem(sanPham: SinhVien, id: Int, onEdit: (SinhVien) -> Unit, onDelete: (SinhVien) -> Unit, onInfo: (SinhVien) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onInfo(sanPham) }
            .padding(5.dp)
            .padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff2F2D2D), shape = RoundedCornerShape(10.dp))
                    ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Mã sinh viên: " + sanPham.PH41939_mssv,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Điểm trung bình: ${(sanPham.PH41939_diemTB).toInt()}",
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Tên sinh viên: ${sanPham.PH41939_tensv}",
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (sanPham.PH41939_trangthai) "Tình trạng: Đã tốt nghiệp" else "Tình trạng: Chưa tốt nghiệp",
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row {
                    IconButton(onClick = {
                        onEdit(sanPham)
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "", tint = Color.White)
                    }
                    IconButton(onClick = { onDelete(sanPham) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AddUpdateDialogCard(
    title: String,
    sanPham: SinhVien? = null,
    onConfirm: (String, Double, String, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var PH41939_mssv by remember { mutableStateOf(sanPham?.PH41939_mssv ?: "") }
    var PH41939_diemTB by remember { mutableStateOf(sanPham?.PH41939_diemTB.toString())}
    var PH41939_tensv by remember { mutableStateOf(sanPham?.PH41939_tensv ?: "") }
    val isChecked = remember { mutableStateOf(sanPham?.PH41939_trangthai ?: false) }
    var showThongBao by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

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
                text = title,
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
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB703),
                        contentColor = Color.White
                    )
                ) {
                    Text("Hủy")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val priceDouble = PH41939_diemTB.toDoubleOrNull()
                        if (PH41939_mssv.trim().isEmpty() || PH41939_diemTB.trim().isEmpty() || PH41939_tensv.trim().isEmpty()) {
                            dialogMessage = "Vui lòng điền đầy đủ các trường thông tin!"
                            showThongBao = true
                        } else if (priceDouble == null) {
                            dialogMessage = "Điểm phải là số!"
                            showThongBao = true
                        } else if (priceDouble < 0 || priceDouble > 10) {
                            dialogMessage = "Điểm phải lớn hơn 0 và nhỏ hơn 10"
                            showThongBao = true
                        } else {
                            onConfirm(PH41939_mssv, priceDouble, PH41939_tensv, isChecked.value)
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



