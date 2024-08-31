package com.nabeelkm.finance.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabeelkm.finance.models.Task
import java.time.LocalDateTime

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

val tasks: List<Task> = listOf(
    Task("LMAO", LocalDateTime.now(), amount = 2923992),
    Task("Task 2", LocalDateTime.now(), amount = 3000000),
    Task("Task 3", LocalDateTime.now(), amount = 250000),
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionListView() {
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
                items(tasks) { task ->
                    ListItem(
                        secondaryText = { Text(task.time.toString()) },
                        trailing = { Text(task.amount.toString()) }
                    ) {
                        Text(task.title)
                    }
                }
            }
        }
    }
}