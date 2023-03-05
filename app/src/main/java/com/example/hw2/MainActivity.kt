package com.example.hw2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hw2.ui.theme.HW2Theme

class MainActivity : ComponentActivity() {

    // Note: I am taking only Integers as inputs, Doubles not accepted
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gradesWithMultipleInputs = mapOf(
                "Homework" to remember { mutableStateListOf(GradeInputModel("4")) }
            )
            val grades = mapOf(
                "Participation" to remember { mutableStateOf(GradeInputModel("76")) },
                "Presentation" to remember { mutableStateOf(GradeInputModel("4")) },
                "Midterm 1" to remember { mutableStateOf(GradeInputModel("90")) },
                "Midterm 2" to remember { mutableStateOf(GradeInputModel("64")) },
                "Final Project" to remember { mutableStateOf(GradeInputModel("98")) },
            )
            var result by remember { mutableStateOf("") }
            fun isValidated(): Boolean {
                return gradesWithMultipleInputs
                    .flatMap { it.value }
                    .union(grades.map { it.value.value })
                    .all {
                        it.error.isEmpty()
                    }
            }

            HW2Theme(darkTheme = false) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomLayout(
                                result = result,
                                isValidated = isValidated(),
                                onCalculationClick = {
                                    // calculation average for multiple grade inputs
                                    // mapping name to grade
                                    val averageGradesForMultipleInputs = gradesWithMultipleInputs
                                        .mapValues { e ->
                                            e.value
                                                .map { it.value.toInt() }
                                                .average()
                                        }

                                    // mapping name to grade
                                    val g2 =
                                        grades.mapValues { it.value.value.value.toDouble() }


                                    val finalGrade =
                                        calculateGrade(averageGradesForMultipleInputs + g2)

                                    result = String.format("%.2f", finalGrade)
                                })
                        })
                    { pv ->
                        // MAIN layout
                        Column(
                            Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = pv.calculateBottomPadding(),
                                ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            gradesWithMultipleInputs.forEach {
                                MultipleGradeInput(label = it.key, grades = it.value)
                            }
                            grades.forEach {
                                GradeInput(
                                    label = it.key,
                                    gradeInputModel = it.value.component1(),
                                    onValueChange = it.value.component2()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun BottomLayout(result: String, isValidated: Boolean, onCalculationClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .shadow(1.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = result.ifEmpty { "Final Result" })
        Button(onClick = onCalculationClick, enabled = isValidated) {
            Text(text = "Calculate")
        }
    }
}

