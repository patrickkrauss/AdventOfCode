package AdventOfCode.DaySeven

import AdventOfCode.ReadInputFromFile

// https://adventofcode.com/2017/day/7
private class DaySeven {

    companion object {
        fun InputToListOfPrograms(listOfProgramsAsString: List<String>): List<Program> {

            fun formatter(stringProgram: String): Program {

                val nameOfProgramsAbove = try {
                    stringProgram.split("-> ")[1].splitToSequence(", ").toList()
                } catch (e: IndexOutOfBoundsException) {
                    null
                }
                val program = Program(stringProgram.substring(0..stringProgram.indexOfFirst { it == ' ' } - 1),
                        stringProgram.substring(stringProgram.indexOfFirst { it == '(' } + 1, stringProgram.indexOfFirst { it == ')' }).toInt(), nameOfProgramsAbove)
                return program
            }

            val listOfPrograms = mutableListOf<Program>()
            listOfProgramsAsString.forEach {
                listOfPrograms.add(formatter(it))
            }
            return listOfPrograms
        }
    }

    data class Program(val name: String, val weight: Int, val nameOfProgramsAbove: List<String>?)

    fun partOne(listOfPrograms: List<Program>): String {

        fun getLastProgramsOfTower(): HashSet<String> {
            val bottomPrograms = mutableSetOf<String>()
            listOfPrograms.forEach {
                if (it.nameOfProgramsAbove == null)
                    bottomPrograms.add(it.name)
            }
            return bottomPrograms.toHashSet()
        }

        fun searchForParents(listOfChildren: HashSet<String>): HashSet<String> {
            if (listOfChildren.count() == 1)
                return listOfChildren.toHashSet()
            val parents = mutableSetOf<String>() // use set because, must not return same parent twice

            listOfPrograms.forEach { p: Program ->
                p.nameOfProgramsAbove?.let {
                    it.forEach { s: String ->
                        listOfChildren.forEach { s1: String ->
                            if (s1 == s) parents.add(p.name)
                        }
                    }
                }
            }
            return parents.toHashSet()
        }


        fun searchForPrimalProgram(): String {
            fun loopSearchForParent(list: HashSet<String>): String {
                if (list.count() == 1) // if there is only 1 parent, primal program found
                    return list.first()
                else
                    return loopSearchForParent(searchForParents(list))
            }

            return loopSearchForParent(getLastProgramsOfTower())
        }


        fun exe(): String { // return primal program
            return searchForPrimalProgram()
        }

        return exe()
    }

    fun partTwo(listOfPrograms: List<Program>): Int {

        fun getParent(child: Program): Program {
            listOfPrograms.forEach { p: Program ->
                p.nameOfProgramsAbove?.let {
                    it.forEach { s: String ->
                        if (s == child.name) return p
                    }
                }
            }
            throw Exception("Parent not found")
        }

        fun searchProgram(name: String): Program {
            return listOfPrograms.find { it.name == name } ?: throw Exception("name not found")
        }

        fun getChild(parents: Program): List<Program> {
            return parents.nameOfProgramsAbove?.map { s -> searchProgram(s) } ?: throw Exception()
        }

        fun getWeightOfProgram(program: Program): Int {
            var weight = program.weight
            var count = 0
            if (program.nameOfProgramsAbove == null)
                return weight
            val programs = program.nameOfProgramsAbove.map { searchProgram(it) }
            while (count < programs.count()) {
                weight += getWeightOfProgram(programs[count])
                count++
            }
            return weight
        }

        fun getProgramWithDiferentWeight(programs: List<Pair<Program, Int>>): Program {

            if (programs.map { it.second }.distinct().count() == 1)
                throw Exception("Found wrong weight")
            val weighs = programs.map { it.second }.toHashSet()
            val quantityOfWeight = mutableMapOf<Int, Int>()  // (weight,quantity)
            weighs.forEach { quantityOfWeight.put(it, 0) }
            for (p in programs) {
                quantityOfWeight[p.second] = quantityOfWeight.getValue(p.second) + 1
            }
            val weightWithLessRepetition = quantityOfWeight.filterValues { value -> value == quantityOfWeight.values.min() }.keys.first()
            return programs.find { it.second == weightWithLessRepetition }!!.first
        }

        fun returnCorrectWeightOfProgram(program: Program): Int {
            val brother = getChild(getParent(program)).filterNot { it.name == program.name }.first()
            return program.weight - (getWeightOfProgram(program) - getWeightOfProgram(brother))
        }

        fun searchWrongWeight(program: Program): Int {
            val childrenOfProgram = program.nameOfProgramsAbove!!.map { searchProgram(it) }

            val childrenOfRootAndWeights = program.nameOfProgramsAbove.map { s -> val p = searchProgram(s);Pair(p, getWeightOfProgram(p)) }

            lateinit var programWithWrongWeight: Program
            try {
                programWithWrongWeight = getProgramWithDiferentWeight(childrenOfRootAndWeights)
            } catch (e: Exception) {
                return returnCorrectWeightOfProgram(getParent(childrenOfProgram.first()))
            }
            return searchWrongWeight(programWithWrongWeight)
        }

        fun exe(): Int {
            val rootProgram = DaySeven().partOne(listOfPrograms)
            return searchWrongWeight(searchProgram(rootProgram))
        }

        return exe()

    }

}


fun main(args: Array<String>) {
    val input = DaySeven.InputToListOfPrograms(ReadInputFromFile("DaySeven"))
    println("Part One: " + DaySeven().partOne(input))
    println("Part Two: " + DaySeven().partTwo(input))
}
