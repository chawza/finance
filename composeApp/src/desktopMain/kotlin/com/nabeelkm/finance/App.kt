package com.nabeelkm.finance

import DriverFactory
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nabeelkm.finance.views.TransactionListView
import com.nabeelkm.finance.views.TransactionFormView
import createDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class Routes {
    TRANSACTION_LIST,
    ADD_TRANSACTION_ITEM,
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val db = remember { createDatabase(DriverFactory()) }
        NavHost(navController, startDestination = Routes.TRANSACTION_LIST.name) {
            composable(Routes.TRANSACTION_LIST.name) {
                TransactionListView(
                    db = db,
                    handleAddTransactionItem = {
                        navController.navigate(Routes.ADD_TRANSACTION_ITEM.name)
                    }
                )
            }
            composable(Routes.ADD_TRANSACTION_ITEM.name) {
                TransactionFormView(
                    db = db
                )
            }
        }
    }
}