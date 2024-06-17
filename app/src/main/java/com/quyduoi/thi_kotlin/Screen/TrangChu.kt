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
import com.quyduoi.thi_kotlin.Model.XeHoi
//import com.quyduoi.thi_kotlin.Model.SanPham
import com.quyduoi.thi_kotlin.ViewModel.SanPhamViewModel

@Composable
fun TrangChu(viewModel: SanPhamViewModel) {
    val sanPhamLists by viewModel.sanPhams.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var showDialogThem by remember { mutableStateOf(false) }
    var showDialogSua by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    var selectedSanPham by remember { mutableStateOf<XeHoi?>(null) }

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
            Text(text = "Quản lý sản phẩm", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                contentAlignment = Alignment.BottomEnd) {
                Button(onClick = {
                    showDialogThem = true
                }) {
                    Text(text = "Thêm sản phẩm")
                }
            }
            LazyColumn {
                itemsIndexed(sanPhamLists) { index, sanPham ->
                    SanPhamItem(sanPham, index + 1,
                        onEdit = {
                            selectedSanPham = sanPham
                            showDialogSua = true
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
                message = "Bạn có chắc chắn muốn xóa món ${selectedSanPham?.name_PH41939}?",
                onConfirmClick = {
                    selectedSanPham?.let { sanPham ->
                        viewModel.xoa(sanPham._id)
                        Toast.makeText(context, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show()
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
                title = "Thêm sản phẩm",
                onDismiss = { showDialogThem = false },
                onConfirm = { name, price, model, status ->
                    val newSanPham = XeHoi(
                        _id = "",
                        name_PH41939 = name,
                        price_PH41939 = price,
                        PH41939_model = model,
                        PH41939_status = status
                    )
                    viewModel.them(newSanPham)
                    Toast.makeText(context, "Thêm sản phẩm mới thành công!", Toast.LENGTH_SHORT).show()
                    showDialogThem = false
                }
            )
        }
        if (showDialogSua && selectedSanPham != null) {
            AddUpdateDialogCard(
                title = "Sửa sản phẩm",
                sanPham = selectedSanPham,
                onDismiss = { showDialogSua = false },
                onConfirm = { name, price, model, status ->
                    selectedSanPham?.let { sanPham ->
                        val updatedSanPham = sanPham.copy(
                            name_PH41939 = name,
                            price_PH41939 = price,
                            PH41939_model = model,
                            PH41939_status = status
                        )
                        viewModel.sua(sanPham._id, updatedSanPham)
                        Toast.makeText(context, "Cập nhât sản phẩm thành công!", Toast.LENGTH_SHORT).show()
                    }
                    showDialogSua = false
                    selectedSanPham = null
                }
            )
        }
        if (showInfo && selectedSanPham != null) {
            InformationDialogCard(
                title = "Thông tin sản phẩm",
                sanPham = selectedSanPham!!,
                onClose = { showInfo = false }
            )
        }
    }
}

@Composable
fun SanPhamItem(sanPham: XeHoi, id: Int, onEdit: (XeHoi) -> Unit, onDelete: (XeHoi) -> Unit, onInfo: (XeHoi) -> Unit) {
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
                        text = "Tên xe: " + sanPham.name_PH41939,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Giá xe: ${(sanPham.price_PH41939).toInt()} đ",
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = sanPham.PH41939_model,
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (sanPham.PH41939_status) "Sản phẩm mới" else "Sản phẩm cũ",
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row {
                    IconButton(onClick = { onEdit(sanPham) }) {
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
    sanPham: XeHoi? = null,
    onConfirm: (String, Float, String, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var name_PH41939 by remember { mutableStateOf(sanPham?.name_PH41939 ?: "") }
    var price_PH41939 by remember { mutableStateOf(sanPham?.price_PH41939.toString())}
    var PH41939_model by remember { mutableStateOf(sanPham?.PH41939_model ?: "") }
    val isChecked = remember { mutableStateOf(sanPham?.PH41939_status ?: false) }
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
                value = name_PH41939,
                onValueChange = { name_PH41939 = it },
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Enter name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 10.dp, top = 25.dp),
                maxLines = 1
            )
            OutlinedTextField(
                value = price_PH41939,
                onValueChange = { price_PH41939 = it },
                label = { Text(text = "Price") },
                placeholder = { Text(text = "Enter Price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 10.dp, top = 25.dp),
                maxLines = 1
            )
            OutlinedTextField(
                value = PH41939_model,
                onValueChange = { PH41939_model = it },
                label = { Text(text = "Model") },
                placeholder = { Text(text = "Enter Model") },
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
                    Text("Hàng mới", modifier = Modifier.padding(start = 8.dp, top = 15.dp))
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
                        val priceDouble = price_PH41939.toFloatOrNull() ?: 0.0f
                        if (name_PH41939.trim().isEmpty() || price_PH41939.trim().isEmpty() || PH41939_model.trim().isEmpty()) {
                            dialogMessage = "Vui lòng điền đầy đủ các trường thông tin!"
                            showThongBao = true
                        } else if (priceDouble.isNaN() || priceDouble <= 0) {
                            dialogMessage = "Giá phải là số lớn hơn 0!"
                            showThongBao = true
                        } else {
                            onConfirm(name_PH41939, priceDouble, PH41939_model, isChecked.value)
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



