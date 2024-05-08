package org.d3if3124.assessment1.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3124.assessment1.R
import org.d3if3124.assessment1.model.Minuman
import org.d3if3124.assessment1.navigation.Screen
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun VendingMachine(modifier: Modifier) {
        val data = listOf(
            Minuman(1, "Boba", R.drawable.boba, 10000f),
            Minuman(2,"Lemontea", R.drawable.lemontea, 7000f),
            Minuman(3,"Matcha", R.drawable.matcha, 15000f),
            Minuman(4,"Kopi", R.drawable.kopi, 5000f),
            Minuman(5,"Redvelvet", R.drawable.milktea, 8000f),
        )

    var selectedMenu by rememberSaveable {
        mutableIntStateOf(0)
    }
    
    var jumlah by rememberSaveable {
        mutableStateOf("")
    }
    var jumlahError by rememberSaveable {
        mutableStateOf(false)
    }

    val radioOptions = listOf(
        stringResource(R.string.kecil),
        stringResource(R.string.sedang),
        stringResource(R.string.besar),
    )

    val context = LocalContext.current

    var selectedSize by  rememberSaveable {
        mutableStateOf(radioOptions[0])
    }

    var totalHarga by rememberSaveable {
        mutableStateOf("")
    }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OutlinedTextField(
                value = jumlah ,
                onValueChange = {
                                jumlah = it
                },
                label = {
                        Text(text = stringResource(R.string.jumlah))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.LocalDrink, contentDescription = "" )
                },
                placeholder = {
                    Text(text = "0")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = jumlahError,
                trailingIcon = { IconPicker(jumlahError, "") },
                supportingText = { ErrorHint(jumlahError) },
                singleLine = true,
            )
            Spacer(modifier = Modifier.size(30.dp))
            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            ){
                radioOptions.forEach { text ->
                    SizeOption(
                        label = text,
                        isSelected = selectedSize == text,
                        modifier = Modifier
                            .selectable(
                                selected = selectedSize == text,
                                onClick = {
                                    selectedSize = text
                                    jumlahError = (jumlah == "" || jumlah == "0")
                                    if (jumlahError) return@selectable
                                    totalHarga = formatCurrency(
                                        getHargaBySize(
                                            data[selectedMenu].harga,
                                            selectedSize
                                        ) * jumlah.toFloat()
                                    )
                                },
                                role = Role.RadioButton
                            )
                            .padding(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        if (selectedMenu == 0){
                            selectedMenu = data.size - 1
                        } else {
                            selectedMenu--
                        }
                      
                    },
                    modifier = Modifier
                        .padding(top = 24.dp),

                    ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")

                }
                Image(
                    painter = painterResource(data[selectedMenu].imageResId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(132.dp)
                )
                Button(
                    onClick = {
                      if (selectedMenu == data.size - 1){
                          selectedMenu = 0
                      } else {
                          selectedMenu++
                      }
                    },
                    modifier = Modifier
                        .padding(top = 24.dp),

                    ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "")
                }
            }
            Text(
                text = data[selectedMenu].nama,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = formatCurrency(data[selectedMenu].harga),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(onClick = {
                jumlahError = (jumlah == "" || jumlah == "0")
                if(jumlahError) return@Button
                totalHarga = formatCurrency(getHargaBySize(data[selectedMenu].harga, selectedSize) * jumlah.toFloat())
            }) {
                Text(text = stringResource(R.string.total_price))
            }
            Spacer(modifier = Modifier.size(20.dp))

            if(totalHarga != "" && totalHarga != "0"){
                Text(text = "Total Harga:\n $totalHarga")
                IconButton(onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_result,
                            jumlah, selectedSize, data[selectedMenu].nama, data[selectedMenu].harga,
                            totalHarga)
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = stringResource(R.string.bagikan))
                }
            }
        }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

fun getHargaBySize(amount: Float, size: String): Float {
    return when(size){
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

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(id = R.string.input_invalid))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    Scaffold(
        topBar= {
            CenterAlignedTopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name_mini_project)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary

                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = stringResource(R.string.tentang_aplikasi))
                    }
                }
            )
        },
    ) {
            padding -> VendingMachine(Modifier.padding(padding))
    }
}

@Preview(showBackground = true)
@Composable
fun PrevMain(){
    MainScreen(navController = rememberNavController())
}

fun formatCurrency(amount: Float): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("IND", "ID"))
    formatter.currency = Currency.getInstance("IDR")
    return formatter.format(amount)
}
