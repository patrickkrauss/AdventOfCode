package AdventOfCode

import java.io.File

private val systemSeparator = File.separator ?: throw IllegalArgumentException("Invalid separator")
private val programPath = File("").absolutePath

fun ReadInputFromFile(dayOfChallenge: String): List<String> {
    val file = File(programPath + systemSeparator + "src" + systemSeparator + "AdventOfCode" + systemSeparator + dayOfChallenge + systemSeparator + dayOfChallenge + "Input.txt")
    return file.readLines()
}

fun ReadBytesFromFile(dayOfChallenge: String): ByteArray {
    val file = File(programPath + systemSeparator + "src" + systemSeparator + "AdventOfCode" + systemSeparator + dayOfChallenge + systemSeparator + dayOfChallenge + "Input.txt")
    return file.readBytes()
}

fun GetInputFile(dayOfChallenge: String) =
        File(programPath + systemSeparator + "src" + systemSeparator + "AdventOfCode" + systemSeparator + dayOfChallenge + systemSeparator + dayOfChallenge + "Input.txt")