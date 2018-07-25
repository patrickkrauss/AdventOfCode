package AdventOfCode.DayTen

import AdventOfCode.ReadBytesFromFile
import AdventOfCode.ReadInputFromFile

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

        val salt = "17,31,73,47,23"

        fun numberToAscii(number: Int): String {
            if (number == 0) // a 'quick' fix
                return "48"

            val charsOfNumber = mutableListOf<Int>()
            var value = number
            var aux = number
            while (aux > 0) {
                charsOfNumber.add((value % 10).toString().get(0).toInt())
                aux = value / 10
                value = aux
            }
            charsOfNumber.reverse() //put the values in order
            val result = StringBuilder()
            for (c in charsOfNumber)
                result.append(c)
            return result.toString()
        }

        fun stringToAscii(input: String): String {
            val inputAsAscii = StringBuilder(input.length * 2)
            for (number in input.split(",")) {
                inputAsAscii.append(numberToAscii(number.toInt()))
                inputAsAscii.append(44)
            }
            inputAsAscii.deleteCharAt(inputAsAscii.length - 1) // remove the last comma
            inputAsAscii.deleteCharAt(inputAsAscii.length - 1)
            return inputAsAscii.toString()
        }

        fun printAscii(asciiValues: String) {
            val result = StringBuilder()
            for (c in asciiValues.split("44")) {  // split the numbers by comma
                for (i in 0..c.length - 1 step 2) {  // travel the number getting the first 2 chars
                    val numberAscii = c[i].toString() + c[i + 1]
                    val number = numberAscii.toInt() - 48
                    result.append(number)
                }
                result.append(",")
            }
            result.deleteCharAt(result.length - 1)
            println(result.toString())
        }

        fun runRounds(quantityOfRounds: Int) {
            val c = "41,54,78,11,1,3,4,48521,255,254"
            printAscii(stringToAscii(c))
        }

        fun exe(): Int {
            runRounds(2)
/*
            val a = stringToAscii("1,2,3")
            println(a)

            val b = printAscii(a)
            println(b)
*/
            return 2
        }

        return exe()

    }

}

fun main(args: Array<String>) {
    println("Part One: " + DayTen().partOne(DayTen.InputToListOfInt(ReadInputFromFile("DayTen"))))


    println("Part Two: " + DayTen().partTwo(ReadBytesFromFile("DayTen")))
}