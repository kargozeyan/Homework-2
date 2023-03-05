package com.example.hw2


val weights = mapOf(
    "Homework" to 0.20,
    "Participation" to 0.10,
    "Presentation" to 0.10,
    "Midterm 1" to 0.10,
    "Midterm 2" to 0.20,
    "Final Project" to 0.30
)

// unit test can be found in 'test' folder
fun calculateGrade(
    grades: Map<String, Double>
): Double {
    var res = 0.0
    grades.forEach {
        res += it.value * weights[it.key]!!
    }

    return res
}