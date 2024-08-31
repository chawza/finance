package com.nabeelkm.finance.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.nabeelkm.finance.Database
import finance.Item
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormView(
    db: Database,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(0) }

    val timeInputState = remember {
        val now = LocalDateTime.now()
        TimePickerState(now.hour, now.minute, true)
    }
    val datePickerState = remember {
        DatePickerState(
            locale = CalendarLocale("Asia/Jakarta"),
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = Instant.now().toEpochMilli(),
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction Item") },
                modifier = Modifier,
                actions = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Filled.ArrowBack, "Back to Transaction List List")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = amount.toString(),
                onValueChange = { amount = it.toInt() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.clickable { showDatePicker = true }
            ) {
                TimeInput(
                    timeInputState,
                )
                Text(
                    text = datePickerState.selectedDateMillis?.let {
                        LocalDate
                            .ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                            .format(DateTimeFormatter.ISO_LOCAL_DATE).toString()
                    } ?: ""
                )
            }
            Button(
                onClick = {
                    lifecycleOwner.lifecycleScope.launch {
                        db.itemQueries.insertFullObject(
                            Item(
                                title = title,
                                amount = amount.toLong(),
                                time = 1725100356000
                            )
                        )
                        navController.popBackStack()
                    }
                },
            ) {
                Text("Add Record")
            }

            if (showDatePicker) {
                Dialog(
                    onDismissRequest = { },
                ) {
                    Surface(
                       shape = MaterialTheme.shapes.medium,
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            IconButton(onClick = { showDatePicker = false}) {
                                Icon(Icons.Filled.Close, "Close Date Picker")
                            }
                        }
                        DatePicker(
                            datePickerState,
                        )
                    }
                }
            }
        }
    }
}