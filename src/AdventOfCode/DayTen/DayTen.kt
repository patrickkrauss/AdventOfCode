package AdventOfCode.DayTen

import AdventOfCode.ReadBytesFromFile
import AdventOfCode.ReadInputFromFile
import java.util.*

// https://adventofcode.com/2017/day/10
private class DayTen {

    companion object {
        fun InputToListOfInt(input: List<String>): List<Int> {
            val result = input.toString().substring(1, input.toString().length - 1).split(",")
            return result.map { it.toInt() }
        }
    }

    fun partOne(lenghts: List<Int>): Int {

        fun createListFrom0To255(): List<Int> {
            val list = MutableList<Int>(256, { 0 })
            for (i in 0..list.size - 1)
                list[i] = i


            return list
        }

        fun multiplyFirstTwoNumbers(list: List<Int>): Int {
            return list[0] * list[1]
        }

        fun getSubList(startPosition: Int, quantity: Int, list: List<Int>): List<Int> {
            val result = mutableListOf<Int>()

            if (startPosition > list.size)
                throw IllegalArgumentException("start cannot be bigger than size")


            var poc = startPosition
            var count = quantity
            while (count > 0) {

                if (poc >= list.size)
                    poc = 0
                result.add(list[poc])
                poc++
                count--
            }
            return result
        }

        fun replaceValues(startPosition: Int, values: List<Int>, oldList: List<Int>): List<Int> {
            val newList = oldList.toMutableList()

            var poc = startPosition
            var count = values.count() - 1
            while (count >= 0) {

                if (poc >= oldList.size)
                    poc = 0
                newList[poc] = values[count]
                poc++
                count--
            }

            return newList
        }

        fun exe(): Int {
            var list = createListFrom0To255()
            var currentPoc = 0
            var skipSize = 0

            for (l in lenghts) {
                list = replaceValues(currentPoc, getSubList(currentPoc, l, list), list)
                currentPoc = (currentPoc + l + skipSize) % list.size
                skipSize++
            }

            return multiplyFirstTwoNumbers(list)

        }

        return exe()
    }

    fun partTwo(input: ByteArray): Int {

        fun addBytes(oldValues: ByteArray,newValues: ByteArray): ByteArray {
            return oldValues.plus(','.toByte()).plus(newValues)
        }


        fun exe(): Int {
            val l = listOf<String>("7","31","73","47","23")
            val a = input.plus(l.map { it.toByte() })
            println(Arrays.toString(a))
            return 2
        }

        return exe()

    }

}

fun main(args: Array<String>) {
    println("Part One: " + DayTen().partOne(DayTen.InputToListOfInt(ReadInputFromFile("DayTen"))))


    println("Part Two: " + DayTen().partTwo(ReadBytesFromFile("DayTen")))
}