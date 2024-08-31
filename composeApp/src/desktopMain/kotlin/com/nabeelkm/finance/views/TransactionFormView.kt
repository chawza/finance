import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import java.time.Instant
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormView() {
    var title by remember { mutableStateOf("") }
    val time by remember { mutableStateOf(Instant.now()) }
    var amount by remember { mutableStateOf(0) }

    val timeInputState = remember {
        val now = LocalDateTime.now()
        TimePickerState(now.hour, now.minute, true)
    }
    val datePickerState = remember {
        DatePickerState(
            locale = CalendarLocale("Asia/Jakarta"),
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it},
            )
            OutlinedTextField(
                value = amount.toString(),
                onValueChange = { amount = it.toInt() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            TimeInput(
                timeInputState,
            )
            DatePicker(
                datePickerState
            )
        }
    }
}