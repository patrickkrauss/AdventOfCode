package AdventOfCode.DayNine

import AdventOfCode.ReadInputFromFile

// https:// adventofcode.com/2017/day/9
private class DayNine {

    companion object reader {
        fun InputToCharArray(input: List<String>): CharArray {
            val result = input.toString().toCharArray()
            return result.slice(1..result.size - 2).toCharArray()
        }
    }

    fun partOne(input: CharArray): Int {

        fun findTotalScore(): Int {
            var score = 0
            var openCurlyBraces = 0
            var havaFoundCurlyBraces = false
            var poc = 0
            var garbage = false

            while (poc < input.size) {
                if (!garbage) {
                    val a = input[poc]
                    if (input[poc] == '<') {
                        garbage = true
                    } else {
                        if (input[poc] == '!') {
                            poc++
                        }
                        if (input[poc] == '{') {
                            openCurlyBraces++
                            havaFoundCurlyBraces = true
                        } else if (input[poc] == '}') {
                            openCurlyBraces--
                        }
                        if (havaFoundCurlyBraces)
                            score += openCurlyBraces
                    }
                } else {
                    if (input[poc] == '!') {
                        poc++
                    } else if (input[poc] == '>')
                        garbage = false
                }
                havaFoundCurlyBraces = false
                poc++
            }

            return score
        }

        fun exe(): Int {
            return findTotalScore()
        }

        return exe()
    }

    fun partTwo(input: CharArray): Int {

        fun CountGarbageCharacters(): Int {

            var poc = 0
            var garbage = false
            var garbageQnt = 0

            while (poc < input.size) {
                if (!garbage) {
                    val a = input[poc]
                    if (input[poc] == '<') {
                        garbage = true
                    }
                } else {
                    val b = input[poc]
                    if (input[poc] == '>') {
                        garbage = false
                    } else if (input[poc] == '!') {
                        poc++
                    } else {
                        garbageQnt++
                    }
                }
                poc++
            }

            return garbageQnt
        }

        fun exe(): Int {
            return CountGarbageCharacters()
        }

        return exe()
    }

}

fun main(args: Array<String>) {
    val input = DayNine.InputToCharArray(ReadInputFromFile("DayNine"))
    println("Part One: " + DayNine().partOne(input))
    println("Part Two: " + DayNine().partTwo(input))
}