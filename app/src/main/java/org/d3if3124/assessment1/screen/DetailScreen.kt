package org.d3if3124.assessment1.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3124.assessment1.R
import org.d3if3124.assessment1.model.DataMinuman
import org.d3if3124.assessment1.model.Minuman
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController) {

    val data = DataMinuman.listMinuman
    val radioOptions = listOf(
        stringResource(R.string.kecil),
        stringResource(R.string.sedang),
        stringResource(R.string.besar),
    )

    var selectedMenu by rememberSaveable {
        mutableIntStateOf(0)
    }
    var jumlah by rememberSaveable {
        mutableStateOf("")
    }
    var namaPembeli by rememberSaveable {
        mutableStateOf("")
    }
    var selectedSize by rememberSaveable {
        mutableStateOf(radioOptions[0])
    }

    var error by rememberSaveable {
        mutableStateOf(false)
    }


    var totalHarga by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.tambah_order))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormOrder(
            selectedMenu = selectedMenu,
            onMenuChange = {
                selectedMenu = it

                error = (jumlah == "0" || jumlah == "")
                if (error) {
                    jumlah = ""
                    totalHarga = ""
                    return@FormOrder
                }
                totalHarga = formatCurrency(
                    getHargaBySize(
                        data[selectedMenu].harga,
                        selectedSize
                    ) * jumlah.toFloat()
                )
            },
            jumlah = jumlah,
            onJumlahChange = {
                jumlah = it

                error = (jumlah == "0" || jumlah == "")
                if (error) {
                    jumlah = ""
                    totalHarga = ""
                    return@FormOrder
                }
                totalHarga = formatCurrency(
                    getHargaBySize(
                        data[selectedMenu].harga,
                        selectedSize
                    ) * jumlah.toFloat()
                )
            },
            namaPembeli = namaPembeli,
            onNamaPembeliChange = {
                namaPembeli = it

                error = (jumlah == "0" || jumlah == "" || namaPembeli == "")
                if (error) {
                    jumlah = ""
                    namaPembeli = ""
                    totalHarga = ""
                    return@FormOrder
                }
                totalHarga = formatCurrency(
                    getHargaBySize(
                        data[selectedMenu].harga,
                        selectedSize
                    ) * jumlah.toFloat()
                )
            },
            selectedSize = selectedSize,
            onSizeChange = {
                selectedSize = it

                error = (jumlah == "0" || jumlah == "")
                if (error) {
                    jumlah = ""
                    totalHarga = ""
                    return@FormOrder
                }
                totalHarga = formatCurrency(
                    getHargaBySize(
                        data[selectedMenu].harga,
                        selectedSize
                    ) * jumlah.toFloat()
                )
            },
            totalHarga = totalHarga,
            radioOptions = radioOptions,
            data = data,
            modifier = Modifier.padding(padding),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormOrder(
    selectedMenu: Int, onMenuChange: (Int) -> Unit,
    namaPembeli: String, onNamaPembeliChange: (String) -> Unit,
    jumlah: String, onJumlahChange: (String) -> Unit,
    selectedSize: String, onSizeChange: (String) -> Unit,
    totalHarga: String,
    radioOptions: List<String>,
    data: List<Minuman>,
    modifier: Modifier
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        /**
         * Image Minuman
         */
        Image(
            painter = painterResource(data[selectedMenu].imageResId),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .size(132.dp)
        )

        /**
         * Menu minuman Dropdown
         */
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                value = stringResource(
                    R.string.menuDropdown,
                    data[selectedMenu].nama,
                    data[selectedMenu].harga
                ),
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                placeholder = { Text(text = "0") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.LocalDrink, contentDescription = "")
                },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                data.forEach { minuman ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                stringResource(
                                    R.string.menuDropdown,
                                    minuman.nama,
                                    minuman.harga
                                )
                            )
                        },
                        onClick = {
                            onMenuChange((minuman.id - 1.toLong()).toInt())
                            expanded = false
                        },
                        enabled = minuman != data[selectedMenu]
                    )
                }
            }
        }

        /**
         * Nama Pembeli inputField
         */
        OutlinedTextField(
            value = namaPembeli,
            onValueChange = {
                onNamaPembeliChange(it)
            },
            label = {
                Text(text = stringResource(R.string.nama_pembeli))
            },
            placeholder = {
                Text(text = "0")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        /**
         * Jumlah inputField
         */
        OutlinedTextField(
            value = jumlah,
            onValueChange = {
                onJumlahChange(it)
            },
            label = {
                Text(text = stringResource(R.string.jumlah))
            },
            placeholder = {
                Text(text = "0")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        /**
         * Size Radio options
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                SizeOption(
                    label = text,
                    isSelected = selectedSize == text,
                    modifier = Modifier
                        .selectable(
                            selected = selectedSize == text,
                            onClick = {
                                onSizeChange(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(16.dp)
                        .weight(1f)
                )
            }
        }

        if (totalHarga != "" && totalHarga != "0") {
            Text(
                text = "Total Harga:\n $totalHarga",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}


fun getHargaBySize(amount: Float, size: String): Float {
    return when (size) {
        "Kecil" -> amount
        "Sedang" -> amount + 2000f
        "Besar" -> amount + 5000f
        else -> amount
    }
}

@Composable
fun SizeOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrevDetail() {
    DetailScreen(navController = rememberNavController())
}

fun formatCurrency(amount: Float): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("IND", "ID"))
    formatter.currency = Currency.getInstance("IDR")
    return formatter.format(amount)
}