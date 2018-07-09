package AdventOfCode.DayOne

import AdventOfCode.ReadInputFromFile

// https://adventofcode.com/2017/day/1
private class DayOne {

    companion object {
        fun InputToListOfInt(input: List<String>) = input.flatMap { it.map { it.toString().toInt() } }
    }

    fun partOne(list: List<Int>): Int {
        var sum: Int = 0
        var cont: Int = 0
        while (cont < list.size - 1) {
            if (list[cont] == list[cont + 1]) {
                sum += list[cont]
            }
            cont++
        }
        if (list.last() == list.first())
            sum += list.first()
        return sum
    }

    fun partTwo(list: List<Int>): Int {
        var sum: Int = 0
        val listCount: Int = list.count() - 1
        val aux: Int = listCount / 2
        fun getNextPosition(index: Int): Int {
            if ((index + 1) + aux > listCount) {
                return (index + aux) - listCount
            } else {
                return (index + 1) + aux
            }
        }
        for (i in list.indices) {
            if (list[i] == list[getNextPosition(i)]) {
                sum += list[i]
            }
        }
        return sum
    }

}

fun main(args: Array<String>) {
    val input = DayOne.InputToListOfInt(ReadInputFromFile("DayOne"))
    println("Part One: " + DayOne().partOne(input))
    println("Part Two: " + DayOne().partTwo(input))
}