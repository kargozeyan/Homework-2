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

    companion object {
        @JvmStatic
        private fun grades(): Stream<Arguments> = Stream.of(
            Arguments.of(0, 0, 0, 0, 0, 0, 0.0),
            Arguments.of(100, 100, 100, 100, 100, 100, 100.0),
            Arguments.of(73, 87, 91, 68, 75, 92, 82.5),
            Arguments.of(85, 95, 82, 90, 93, 88, 81.5),
            Arguments.of(78, 89, 92, 87, 83, 81, 84.8),
            Arguments.of(66, 76, 84, 92, 85, 70, 82.5),
            Arguments.of(95, 98, 93, 96, 92, 97, 76.5),
            Arguments.of(100, 0, 0, 0, 0, 0, 10.0),
            Arguments.of(90, 85, 95, 75, 70, 60, 77.5),
            Arguments.of(75, 85, 90, 80, 80, 85, 84.5)
        )
    }
}
