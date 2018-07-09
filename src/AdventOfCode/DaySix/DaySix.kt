package AdventOfCode.DaySix

import AdventOfCode.ReadInputFromFile

// https://adventofcode.com/2017/day/6
private class DaySix {

    companion object {
        fun InputToListOfInt(input: List<String>): List<Int> {
            val result = input.toString().substring(1, input.toString().length - 1).split("\t")
            return result.map { it.toInt() }
        }
    }

    fun partOne(initialBanksList: List<Int>): Int {
        val listOfConfigurations = mutableListOf<List<Int>>()

        fun verifyIfConfigurationAlreadyHappened(listOfBanks: List<Int>): Boolean {
            listOfConfigurations.forEach {
                if (it == listOfBanks) return true
            }
            return false
        }

        fun searchForGreaterBank(banks: List<Int>): Int { // return the position of the greater bank
            var greater = Integer.MIN_VALUE
            for (i in banks) {
                if (i > greater)
                    greater = i
            }
            return banks.indexOf(greater)
        }

        fun reditrubuteValuesBetweenBanks(banksConfiguration: List<Int>): List<Int> { // returns the new configuration of banks
            val newConfigurationOfBanks = banksConfiguration.toMutableList()
            val size = banksConfiguration.size - 1
            val greaterBank = searchForGreaterBank(banksConfiguration)
            var quantityOfBlocks = banksConfiguration[greaterBank]
            newConfigurationOfBanks[greaterBank] = 0
            var index = greaterBank + 1
            while (quantityOfBlocks > 0) {
                if (index > size) {
                    index = 0
                }
                newConfigurationOfBanks[index] = newConfigurationOfBanks[index] + 1
                quantityOfBlocks--
                index++
            }
            return newConfigurationOfBanks
        }

        fun searchForRepetedConfiguration(initialBanksConfiguration: List<Int>): Int {
            listOfConfigurations.add(initialBanksConfiguration)
            var banksConfiguration = initialBanksConfiguration
            var redistributionCycles = 0
            while (true) {
                listOfConfigurations.add(banksConfiguration)
                redistributionCycles++
                banksConfiguration = reditrubuteValuesBetweenBanks(banksConfiguration);
                if (verifyIfConfigurationAlreadyHappened(banksConfiguration)) {
                    return redistributionCycles
                }

            }
        }

        fun exe(): Int {
            return searchForRepetedConfiguration(initialBanksList)
        }
        return exe()
    }

    fun partTwo(initialBanksList: List<Int>): Int {
        val listOfConfigurations = mutableListOf<List<Int>>()

        fun verifyIfConfigurationAlreadyHappened(listOfBanks: List<Int>): Boolean {
            listOfConfigurations.forEach {
                if (it == listOfBanks) return true
            }
            return false
        }

        fun searchForGreaterBank(banks: List<Int>): Int { // return the position of the greater bank
            var greater = Integer.MIN_VALUE
            for (i in banks) {
                if (i > greater)
                    greater = i
            }
            return banks.indexOf(greater)
        }

        fun reditrubuteValuesBetweenBanks(banksConfiguration: List<Int>): List<Int> { // returns the new configuration of banks
            val newConfigurationOfBanks = banksConfiguration.toMutableList()
            val size = banksConfiguration.size - 1
            val greaterBank = searchForGreaterBank(banksConfiguration)
            var quantityOfBlocks = banksConfiguration[greaterBank]
            newConfigurationOfBanks[greaterBank] = 0
            var index = greaterBank + 1
            while (quantityOfBlocks > 0) {
                if (index > size) {
                    index = 0
                }
                newConfigurationOfBanks[index] = newConfigurationOfBanks[index] + 1
                quantityOfBlocks--
                index++
            }
            return newConfigurationOfBanks
        }

        fun searchForRepetedConfiguration(initialBanksConfiguration: List<Int>): Int {
            listOfConfigurations.clear()
            listOfConfigurations.add(initialBanksConfiguration)
            var banksConfiguration = initialBanksConfiguration
            var redistributionCycles = 0
            while (true) {
                listOfConfigurations.add(banksConfiguration)
                redistributionCycles++
                banksConfiguration = reditrubuteValuesBetweenBanks(banksConfiguration);
                if (verifyIfConfigurationAlreadyHappened(banksConfiguration)) {
                    return redistributionCycles
                }

            }
        }

        fun searchForTwoRepetedConfigurations(): Int {
            searchForRepetedConfiguration(initialBanksList)
            return searchForRepetedConfiguration(listOfConfigurations.last())
        }

        fun exe(): Int {
            return searchForTwoRepetedConfigurations()
        }
        return exe()
    }


}

fun main(args: Array<String>) {
    val input = DaySix.InputToListOfInt(ReadInputFromFile("DaySix"))
    println("Part One: " + DaySix().partOne(input))
    println("Part Two: " + DaySix().partTwo(input))
}