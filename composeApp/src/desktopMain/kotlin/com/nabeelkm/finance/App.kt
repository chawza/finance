package com.nabeelkm.finance

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.nabeelkm.finance.views.TransactionListView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        TransactionListView()
    }
}