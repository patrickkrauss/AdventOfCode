package AdventOfCode.DayThree

// https://adventofcode.com/2017/day/3
private class DayThree {

    fun partOne(postionInput: Int): Int {

        fun estimateMatrixSize(inputPosition: Int): Int {
            var matrixCapacity = 1 // matrixCapacity == max number of values in matrix
            var arraySize = 1
            while (matrixCapacity < inputPosition) {
                arraySize++
                matrixCapacity = Math.pow(arraySize.toDouble(), 2.toDouble()).toInt()
            }
            return arraySize
        }

        fun searchInitialPosition(matrix: Array<Array<Int>>): Pair<Int, Int> { // return the coordinates of the center of the matrix
            val size = matrix.get(0).size
            return if (size % 2 == 1) Pair(size / 2, size / 2) else Pair(size / 2, size / 2 - 1)
        }

        fun fillTablePositions(postionInput: Int, initialPosition: Pair<Int, Int>, matrix: Array<Array<Int>>): Pair<Int, Int> { // returns the location of the input position
            var (line, col) = initialPosition
            matrix[line][col] = 1 // put value on the initial position
            var actualPositionValue = 2 // value to put in square
            var quantityOfMoves = 1 // quantity of squares per turn
            var lineDirection: Short = 1 // defines if go right or left
            var lineTurn: Boolean = false
            while (true) {
                for (i in 1..quantityOfMoves) {
                    if (lineTurn)
                        line += lineDirection
                    else
                        col += lineDirection
                    matrix[line][col] = actualPositionValue
                    if (actualPositionValue == postionInput) {
                        return Pair(line, col)
                    }
                    actualPositionValue++
                }
                lineTurn = !lineTurn
                if (lineTurn)
                    lineDirection = lineDirection.unaryMinus().toShort() // inverts the direction
                else
                    quantityOfMoves++
            }
        }

        fun returnStepsRequired(postionInputLocation: Pair<Int, Int>, initialPosition: Pair<Int, Int>): Int {
            var steps = 0
            var line = postionInputLocation.first
            var col = postionInputLocation.second
            while (line != initialPosition.first) {
                if (line > initialPosition.first)
                    line--
                else
                    line++
                steps++
            }
            while (col != initialPosition.second) {
                if (col > initialPosition.second)
                    col--
                else
                    col++
                steps++
            }
            return steps
        }

        fun exe(): Int { // return the quantity of steps required
            if (postionInput == 1)
                return 0
            val arrayLenght = estimateMatrixSize(postionInput)
            val matrix: Array<Array<Int>> = Array(arrayLenght, { Array(arrayLenght, { 0 }) })
            return returnStepsRequired(fillTablePositions(postionInput, searchInitialPosition(matrix), matrix), searchInitialPosition(matrix))
        }

        return exe()
    }

    fun partTwo(postionInput: Int): Int {

        fun searchInitialPosition(matrix: Array<Array<Int>>): Pair<Int, Int> { // return the coordinates of the center of the matrix
            val size = matrix.get(0).size
            return if (size % 2 == 1) Pair(size / 2, size / 2) else Pair(size / 2, size / 2 - 1)
        }

        fun doubleMatrixSize(matrix: Array<Array<Int>>): Array<Array<Int>> {
            val newSize = matrix[1].size * 2
            return Array(newSize, { Array(newSize, { 0 }) })
        }

        fun returnNearbyPositions(actualPosition: Pair<Int, Int>): List<Pair<Int, Int>> {
            val listOfLocations = arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
            val positionsNearby = mutableListOf<Pair<Int, Int>>()
            for (l in listOfLocations) {
                positionsNearby.add(Pair(actualPosition.first + l.first, actualPosition.second + l.second))
            }
            return positionsNearby
        }

        fun returnSumOfNearbyPostions(matrix: Array<Array<Int>>, listOfPositions: List<Pair<Int, Int>>): Int {
            var sum = 0
            listOfPositions.forEach {
                try {
                    sum += matrix[it.first][it.second]
                } catch (e: ArrayIndexOutOfBoundsException) {

                }
            }
            return sum
        }

        fun fillTablePositions(initialPosition: Pair<Int, Int>, matrix: Array<Array<Int>>): Int { // returns the location of the input position
            var (line, col) = initialPosition
            matrix[line][col] = 1 // put value on the initial position
            var actualPositionValue = 1 // value to put in square
            var quantityOfMoves = 1 // quantity of squares per turn
            var lineDirection: Short = 1 // defines if go right or left
            var lineTurn: Boolean = false
            while (true) {
                for (i in 1..quantityOfMoves) {
                    if (lineTurn)
                        line += lineDirection
                    else
                        col += lineDirection
                    actualPositionValue = returnSumOfNearbyPostions(matrix, returnNearbyPositions(Pair(line, col)))
                    matrix[line][col] = actualPositionValue
                    if (actualPositionValue > postionInput) {
                        return actualPositionValue
                    }
                }
                lineTurn = !lineTurn
                if (lineTurn)
                    lineDirection = lineDirection.unaryMinus().toShort() // inverts the direction
                else
                    quantityOfMoves++
            }
        }

        fun searchFirstValueGreaterThanInput(matrixParameter: Array<Array<Int>>): Int {
            var matrix = matrixParameter
            while (true) {
                try {
                    return fillTablePositions(searchInitialPosition(matrix), matrix)
                } catch (e: ArrayIndexOutOfBoundsException) {  // if didn't reach input, double matrix size
                    matrix = doubleMatrixSize(matrix)
                }
            }
        }

        fun exe(): Int { // return the quantity of steps required
            val initialArrayLenght = 10
            val matrix: Array<Array<Int>> = Array(initialArrayLenght, { Array(initialArrayLenght, { 0 }) })
            return searchFirstValueGreaterThanInput(matrix)
        }

        return exe()
    }

}


fun main(args: Array<String>) {
    val input = 265149
    println("Part one: " + DayThree().partOne(input))
    println("Part two: " + DayThree().partTwo(input))
}