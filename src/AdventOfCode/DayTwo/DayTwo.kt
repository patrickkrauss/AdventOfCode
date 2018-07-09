package AdventOfCode.DayTwo

import AdventOfCode.ReadInputFromFile
import java.util.*

// https://adventofcode.com/2017/day/2
private class DayTwo {

    companion object {
        fun InputToIntMatrix(input: List<String>): Array<Array<Int>> {
            val tab = Regex("\t")
            lateinit var values: List<String>
            val result = Array(input.size, { Array(input[0].split(tab).size, { 0 }) })
            for ((pocLine, line) in input.withIndex()) {
                values = line.split(tab)
                for ((poc, value) in values.withIndex()) {
                    result[pocLine][poc] = value.toInt()
                }
            }
            result.forEach { Arrays.toString(it) }
            return result
        }
    }

    fun partOne(matrix: Array<Array<Int>>): Int {
        var sum: Int = 0
        var greater = Int.MIN_VALUE
        var smaller = Int.MAX_VALUE
        for (line in matrix) {
            for (c in line) {
                if (c > greater)
                    greater = c
                if (c < smaller)
                    smaller = c
            }
            sum += greater - smaller
            greater = Int.MIN_VALUE
            smaller = Int.MAX_VALUE
        }
        return sum
    }

    fun partTwo(matrix: Array<Array<Int>>): Int {
        var sum: Int = 0
        for (line in matrix) {
            for (col in line) {
                for (aux in line) {
                    if (col % aux == 0 && col != aux) {
                        sum += col / aux
                    }
                }
            }
        }
        return sum
    }

}

fun main(args: Array<String>) {
    val input = DayTwo.InputToIntMatrix(ReadInputFromFile("DayTwo"))
    println("Part one: " + DayTwo().partOne(input))
    println("Part two: " + DayTwo().partTwo(input))
}