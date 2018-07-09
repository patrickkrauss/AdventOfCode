package AdventOfCode.DayEight

import AdventOfCode.ReadInputFromFile

// https://adventofcode.com/2017/day/8
private class DayEight {

    companion object {

        private fun GetRegistersFromFile(instructions: List<String>): HashMap<String, Int> {
            val registers = hashMapOf<String, Int>()
            instructions.forEach { registers.put(GetRegisterName(it), 0) }
            return registers
        }

        private fun GetRegisterName(instruction: String): String {
            val regex = Regex("[a-z]+")
            val result = regex.find(instruction) ?: throw IllegalArgumentException("Register not found")
            return result.value
        }

        private fun BuildInstructions(stringInstructios: List<String>): List<Instruction> {
            val instructions = Array<Instruction?>(stringInstructios.size, { null })
            stringInstructios.forEachIndexed { i: Int, s: String -> instructions[i] = Instruction.buildInstruction(s) }
            return instructions.toList().requireNoNulls()
        }

        private fun GetOperator(instruction: String): Operator {
            val regex = Regex("(inc|dec|==|!=|>=|<=|>|<)")
            val result = regex.find(instruction) ?: throw IllegalArgumentException("Operator not found")
            when (result.value) {
                "inc" -> return Operator.INC
                "dec" -> return Operator.DEC
                "==" -> return Operator.EQUAL
                "!=" -> return Operator.DIFFERENT
                ">" -> return Operator.GREATER
                "<" -> return Operator.SMALLER
                ">=" -> return Operator.GREATER_EQUAL
                "<=" -> return Operator.SMALLER_EQUAL
                else -> throw IllegalArgumentException("Operator not found")
            }
        }

        private fun GetValue(instruction: String): Int {
            val regex = Regex("[-|+]\\d+|\\d+")
            val result = regex.find(instruction) ?: throw IllegalArgumentException("Value not found")
            return result.value.toInt()
        }
    }

    private enum class Operator {
        EQUAL, DIFFERENT, GREATER, SMALLER, GREATER_EQUAL, SMALLER_EQUAL, INC, DEC
    }

    private class InstructionExpression(val register: String, private val value: Int, private val operator: Operator) {

        companion object {
            fun build(instruction: String): InstructionExpression {
                return InstructionExpression(GetRegisterName(instruction), GetValue(instruction), GetOperator(instruction))
            }
        }

        fun validateExpression(valuerInRegister: Int): Boolean {
            when (operator) {
                Operator.EQUAL -> return valuerInRegister == value
                Operator.DIFFERENT -> return valuerInRegister != value
                Operator.GREATER -> return valuerInRegister > value
                Operator.SMALLER -> return valuerInRegister < value
                Operator.GREATER_EQUAL -> return valuerInRegister >= value
                Operator.SMALLER_EQUAL -> return valuerInRegister <= value
                else -> throw IllegalArgumentException("Invalid operator")
            }
        }

        override fun toString(): String {
            return "$register $operator $value"
        }
    }

    private class InstructionAction(private val register: String, private val value: Int, private val operator: Operator) {

        companion object {
            fun buildInstruction(instruction: String): InstructionAction {
                return InstructionAction(GetRegisterName(instruction), GetValue(instruction), GetOperator(instruction))
            }
        }

        fun executeInstruction(registers: HashMap<String, Int>) {
            when (operator) {
                Operator.INC -> registers.put(register, registers[register]!!.plus(value))
                Operator.DEC -> registers.put(register, registers[register]!!.minus(value))
                else -> throw IllegalArgumentException("Invalid operator")
            }
        }

        override fun toString(): String {
            return "$register $operator $value"
        }
    }

    private open class Instruction(val action: InstructionAction, val expression: InstructionExpression) {

        companion object {
            fun buildInstruction(instruction: String): Instruction {
                val instructionAction = InstructionAction.buildInstruction(instruction.split("if")[0])
                val instructionExpression = InstructionExpression.build(instruction.split("if")[1])
                return Instruction(instructionAction, instructionExpression)
            }
        }

        override fun toString(): String {
            return "$action if $expression"
        }
    }

    private class InstructionForCpu(val cpu: Cpu, instruction: Instruction) : Instruction(instruction.action, instruction.expression) {
        fun execute() {
            if (expression.validateExpression(cpu.registers[expression.register]!!))
                action.executeInstruction(cpu.registers)
        }
    }

    private class Program(instructions: List<Instruction>, cpu: Cpu) {
        private val instructions: List<InstructionForCpu> = instructions.map { InstructionForCpu(cpu, it) }

        private fun executeInstruction(instruction: InstructionForCpu) {
            instruction.execute()
        }

        fun runAndGetLargestValueAfterExecution(): Int {
            for (instruction in instructions)
                executeInstruction(instruction)
            return instructions.first().cpu.registers.values.max()!!
        }

        fun runAndReturnLargerValueDuringExecution(): Int {
            val largestValues = mutableSetOf<Int>()
            for (instruction in instructions) {
                executeInstruction(instruction)
                largestValues.add(instruction.cpu.registers.values.max()!!)
            }
            return largestValues.max()!!
        }
    }


    private class Cpu(val registers: HashMap<String, Int>) {

        private lateinit var program: Program

        fun setProgram(program: Program) {
            this.program = program
        }

        fun getLargestValueInRegister(): Int {
            return program.runAndGetLargestValueAfterExecution()
        }

        fun getLargestValueInRegisterDuringExecution(): Int {
            return program.runAndReturnLargerValueDuringExecution()
        }

    }


    fun partOne(input: List<String>): Int {
        fun exe(): Int {
            val instructions = input
            val cpu = Cpu(GetRegistersFromFile(instructions))
            val program = Program(BuildInstructions(instructions), cpu)
            cpu.setProgram(program)
            return cpu.getLargestValueInRegister()
        }

        return exe()
    }

    fun partTwo(input: List<String>): Int {
        fun exe(): Int {
            val instructions = input
            val cpu = Cpu(GetRegistersFromFile(instructions))
            val program = Program(BuildInstructions(instructions), cpu)
            cpu.setProgram(program)
            return cpu.getLargestValueInRegisterDuringExecution()
        }

        return exe()
    }

}

fun main(args: Array<String>) {
    val input = ReadInputFromFile("DayEight")
    println("Part One: " + DayEight().partOne(input))
    println("Part Two: " + DayEight().partTwo(input))
}