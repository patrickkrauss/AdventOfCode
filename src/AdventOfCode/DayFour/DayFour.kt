package AdventOfCode.DayFour

import AdventOfCode.ReadInputFromFile

// https://adventofcode.com/2017/day/4
private class DayFour {

    fun partOne(listOfPassPhrases: List<String>): Int {

        fun isPassphraseValid(passPhrase: String): Boolean { // validate if passphrase have duplicated words
            val setHelper = hashSetOf<String>()
            val listOfWordsInPassphrase = passPhrase.splitToSequence(" ")
            listOfWordsInPassphrase.forEach {
                if (!setHelper.add(it)) // if word already exists passphrase is not valid
                    return false
            }
            return true
        }

        fun exe(): Int { // returns quantity of valid passphrases
            var validPassPhrases = 0
            for (passphrase in listOfPassPhrases) {
                if (isPassphraseValid(passphrase))
                    validPassPhrases++
            }
            return validPassPhrases
        }

        return exe()
    }

    fun partTwo(listOfPassPhrases: List<String>): Int {

        fun isPassphraseValid(passPhrase: String): Boolean {
            val listOfWords = passPhrase.splitToSequence(" ")
            val listOfQuantityOfChars = mutableListOf<Map<Char, Int>>()

            for (word in listOfWords) { // put in the hashmap the chars of each word
                val hashMap = mutableMapOf<Char, Int>()
                for (c in word) {
                    if (hashMap.put(c, hashMap[c] ?: 1) != null) { // validate if char is already in hashmap
                        hashMap[c] = hashMap.get(c)!! + 1
                    }
                }
                listOfQuantityOfChars.add(hashMap)
            }

            for ((k, v) in listOfQuantityOfChars.withIndex()) {
                for ((k2, v2) in listOfQuantityOfChars.withIndex()) {
                    if (k != k2) {   // validate if is not the same word trying to be validated
                        if (v.equals(v2)) { // if hashmap of diferents words are equal, is an anagram
                            return false
                        }
                    }
                }
            }
            return true
        }

        fun exe(): Int { // return the quantity of valid passphrases
            var validPassPhrases = 0
            for (passphrase in listOfPassPhrases) {
                if (isPassphraseValid(passphrase))
                    validPassPhrases++
            }
            return validPassPhrases
        }

        return exe()
    }

}

fun main(args: Array<String>) {
    val input = ReadInputFromFile("DayFour")
    println("Part one: " + DayFour().partOne(input))
    println("Part two: " + DayFour().partTwo(input))
}