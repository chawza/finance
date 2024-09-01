package com.nabeelkm.finance.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nabeelkm.finance.Database
import com.nabeelkm.finance.navigation.Routes
import finance.Item
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListView(
    db: Database,
    navController: NavController,
    handleAddTransactionItem: () -> Unit
) {
    val tasks = remember { mutableStateOf<List<Item>>(listOf()) }

    LaunchedEffect(true) {
        tasks.value = db.itemQueries.SelectAll().executeAsList()
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                actions = {
                    Icon(Icons.Filled.Person, contentDescription = "Profile")
                }
            )
        },
        floatingActionButton = {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add button",
                modifier = Modifier.clickable { handleAddTransactionItem() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn {
                items(tasks.value) { task ->
                    ListItem(
                        overlineContent = {
                            val time = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(task.time),
                                TimeZone.getTimeZone("Asia/Jakarta").toZoneId()
                            )
                            Text(time.format(DateTimeFormatter.ISO_LOCAL_DATE))
                        },
                        headlineContent = {
                            Text(task.title)
                        },
                        trailingContent = {
                            Text(
                                text = "Rp" + String.format("%,d", task.amount).replace(',','.'),
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        },
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Routes.editTransactionById(task.rowid)
                            )
                        }
                    )
                }
            }
        }
    }
}