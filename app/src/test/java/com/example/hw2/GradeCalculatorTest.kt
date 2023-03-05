package com.example.hw2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvFileSource
import java.util.stream.Stream

class GradeCalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = ["/data/grades.csv"], numLinesToSkip = 1)
    fun testGradeCalculator(
        homeworkGrade: Int,
        participationGrade: Int,
        groupPresentationGrade: Int,
        midterm1Grade: Int,
        midterm2Grade: Int,
        finalProjectGrade: Int,
        expected: Double
    ) {
        assertEquals(
            expected,
            calculateGrade(
                mapOf(
                    "Homework" to homeworkGrade.toDouble(),
                    "Participation" to participationGrade.toDouble(),
                    "Presentation" to groupPresentationGrade.toDouble(),
                    "Midterm 1" to midterm1Grade.toDouble(),
                    "Midterm 2" to midterm2Grade.toDouble(),
                    "Final Project" to finalProjectGrade.toDouble()
                )
            ),
            0.001
        )
    }
}
