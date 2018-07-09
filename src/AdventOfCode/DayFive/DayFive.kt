package AdventOfCode.DayFive

import AdventOfCode.ReadInputFromFile

// https://adventofcode.com/2017/day/5
private class DayFive {

    companion object {
        fun InputToListOfInt(input: List<String>) = input.map { it.toInt() }
    }

    fun partOne(input: List<Int>): Int {
        val list = input.toMutableList()

        fun returnStepsRequired(): Int {
            var steps = 0
            var currentPosition = 0
            var lastPosition = 0
            try {
                while (true) {
                    currentPosition += list[currentPosition]
                    list[lastPosition] = list[lastPosition] + 1
                    lastPosition = currentPosition
                    steps++
                }
            } catch (e: IndexOutOfBoundsException) {
                return steps
            }
        }

        return returnStepsRequired()
    }

    fun partTwo(input: List<Int>): Int {
        val list = input.toMutableList()
        fun returnStepsRequired(): Int {
            var steps = 0
            var currentPosition = 0
            var lastPosition = 0
            try {
                while (true) {
                    currentPosition += list[currentPosition]
                    if (list[lastPosition] >= 3)
                        list[lastPosition] = list[lastPosition] - 1
                    else
                        list[lastPosition] = list[lastPosition] + 1
                    lastPosition = currentPosition
                    steps++
                }
            } catch (e: IndexOutOfBoundsException) {
                return steps
            }
        }

        return returnStepsRequired()
    }

}

fun main(args: Array<String>) {
    val input = DayFive.InputToListOfInt(ReadInputFromFile("DayFive"))
    println("Part One: " + DayFive().partOne(input))
    println("Part Two: " + DayFive().partTwo(input))
}



