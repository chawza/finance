package com.nabeelkm.finance.views

import DriverFactory
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import createDatabase
import finance.Item
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone


@Composable
private fun NavigationBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Transactions")
        Icon(Icons.Filled.Person, contentDescription = "Profile")
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionListView() {
    val db = remember { createDatabase(DriverFactory()) }
    val tasks = remember { mutableStateOf<List<Item>>(listOf()) }

    LaunchedEffect(true) {
        tasks.value = db.itemQueries.SelectAll().executeAsList()
    }

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            Icon(Icons.Filled.Add, contentDescription = "Add button")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavigationBar()
            LazyColumn {
                items(tasks.value) { task ->
                    ListItem(
                        secondaryText = {
                            val time = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(task.time),
                                TimeZone.getTimeZone("Asia/Jakarta").toZoneId()
                            )
                            Text(time.format(DateTimeFormatter.ISO_LOCAL_DATE))
                        },
                        trailing = {
                            Text(
                                text = "Rp" + String.format("%,d", task.amount).replace(',','.')
                            )
                        }
                    ) {
                        Text(task.title)
                    }
                }
            }
        }
    }
}