package com.example.hw2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val DEFAULT_VALUE = "100"
const val HINT = "Grade 0-100"

data class GradeInputModel(
    val value: String = DEFAULT_VALUE, val error: String = ""
)

@Composable
fun GradeInput(
    label: String, gradeInputModel: GradeInputModel, onValueChange: (GradeInputModel) -> Unit
) {
    val hasError = gradeInputModel.error.isNotEmpty()
    Column {
        OutlinedTextField(
            value = gradeInputModel.value,
            onValueChange = { input ->
                runCatching {
                    if (input.isEmpty() || input.isBlank()) {
                        throw EmptyInputException()
                    }
                    val number = input.toInt()
                    if (number < 0 || number > 100) {
                        throw OutOfIntervalException()
                    }
                }.onFailure {
                    var errorMessage = it.message!!
                    if (it is NumberFormatException) {
                        errorMessage = "Wrong number format"
                    }
                    onValueChange(GradeInputModel(input, errorMessage))
                }.onSuccess {
                    onValueChange(GradeInputModel(input))
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            label = { Text(label) },
            placeholder = { Text(HINT) },
            isError = hasError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorCursorColor = Color.Red
            ),
            maxLines = 1,
            singleLine = true
        )
        if (hasError) {
            Text(
                gradeInputModel.error,
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 8.sp,
                color = Color.Red
            )
        }
    }
}

@Composable
private fun ActionButton(icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick, shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(36.dp),
    ) {
        Icon(imageVector = icon, contentDescription = "")
    }
}

@Composable
fun MultipleGradeInput(
    label: String, grades: SnapshotStateList<GradeInputModel>
) {
    Column {
        repeat(grades.size) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GradeInput(
                    label = "$label ${it + 1}",
                    gradeInputModel = grades[it],
                    onValueChange = { gradeInputModel -> grades[it] = gradeInputModel }
                )
                if (grades.size > 1) {
                    ActionButton(Icons.Default.Delete, onClick = { grades.removeAt(it) })
                }

                if (it == grades.lastIndex) {
                    ActionButton(Icons.Default.Add, onClick = {
                        grades.add(
                            GradeInputModel(
                                DEFAULT_VALUE
                            )
                        )
                    })
                }
            }
        }
    }
}

private class OutOfIntervalException : Exception("Number is out of interval 0-100")
private class EmptyInputException : Exception("Input is empty")