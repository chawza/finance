package com.nabeelkm.finance.navigation

enum class Routes {
    TRANSACTION_LIST,
    ADD_TRANSACTION_ITEM,
    EDIT_TRANSACTION_ITEM;

    companion object {
        fun editTransactionById(itemId: Long): String {
            return "${EDIT_TRANSACTION_ITEM.name}/$itemId"
        }
    }
}
