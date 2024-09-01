package com.nabeelkm.finance

import DriverFactory
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nabeelkm.finance.navigation.Routes
import com.nabeelkm.finance.views.TransactionFormView
import com.nabeelkm.finance.views.TransactionListView
import createDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview


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
                    navController = navController,
                    handleAddTransactionItem = {
                        navController.navigate(Routes.ADD_TRANSACTION_ITEM.name)
                    },
                )
            }
            composable(Routes.ADD_TRANSACTION_ITEM.name) {
                TransactionFormView(null, db, navController)
            }
            composable(
                "${Routes.EDIT_TRANSACTION_ITEM.name}/{itemId}",
                arguments = listOf(
                    navArgument("itemId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val tasksId = backStackEntry.arguments!!.getString("itemId")!!
                val task = db.itemQueries.getById(tasksId.toLong()).executeAsOneOrNull()

                if (task == null) {
                    navController.navigate(Routes.TRANSACTION_LIST.name)
                    return@composable
                }

                TransactionFormView(
                    task = task,
                    db = db,
                    navController = navController
                )
            }
        }
    }
}