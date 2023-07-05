package com.dna.newsapp.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dna.newsapp.R
import com.dna.newsapp.model.Filter
import com.dna.newsapp.model.sortFilters2
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    filters: List<Filter>,
    onUpdate: (Filter, String) -> Unit,
    sortState: MutableState<Filter>,
    fromDate: MutableState<String>
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedCalendar = Calendar.getInstance()

            selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            fromDate.value = dateFormat.format(selectedCalendar.time)
        },
        year,
        month,
        dayOfMonth
    )

    Surface(shape = MaterialTheme.shapes.large) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.heightIn(min = 56.dp),
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.filter),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = {
                        sortState.value = filters[0]
                        fromDate.value = ""
                        onUpdate(sortState.value, fromDate.value)
                    }) {
                        Text(text = stringResource(R.string.clear))
                    }
                }
            }
            item {
                Text(
                    text = stringResource(id = R.string.shorts),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            items(filters) { filter ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .clickable {
                            sortState.value = filters.find { it.name == filter.name }!!
                        }
                        .fillMaxWidth()
                ) {
                    RadioButton(
                        selected = filter.name == sortState.value.name,
                        onClick = {
                            sortState.value = filter
                        })
                    Text(text = filter.name)
                }
            }
            item {
                Text(
                    text = stringResource(id = R.string.form),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            item {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    value = fromDate.value,
                    onValueChange = {
                        fromDate.value = it
                        datePicker.show()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(text = stringResource(R.string.form_hint))
                    },
                    trailingIcon = {
                        IconButton(onClick = { datePicker.show() }) {
                            Icon(
                                imageVector = Icons.Rounded.EditCalendar,
                                contentDescription = stringResource(R.string.date_picker)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { onUpdate(sortState.value, fromDate.value) }) {
                        Text(text = stringResource(R.string.apply_change))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun FilterBarPreview() {
    val sortState = remember { mutableStateOf(sortFilters2[0]) }
    val fromDate = remember { mutableStateOf("") }
    FilterBar(
        filters = sortFilters2,
        onUpdate = { _, _ -> },
        sortState = sortState,
        fromDate = fromDate,
    )
}