import java.util.*
import kotlin.concurrent.timer
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.floor
import kotlin.time.measureTime

fun main() {
    fun genCombinations(chars: List<Char>, length: Int): List<String> {
        if (length == 0) {
            return listOf("")  // Base case: length 0 produces an empty string
        }
        val result = mutableListOf<String>()
        // Recursive approach to generate combinations
        val smallerCombos = genCombinations(chars, length - 1)
        for (char in chars) {
            for (combo in smallerCombos) {
                result.add(combo + char)
            }
        }
        return result
    }

    fun getOpCombinations(operators: List<Char>, numSz: Int): List<String> {
        var combinations = mutableListOf<String>()
        val combo = genCombinations(operators, numSz)
        combinations.addAll(combo)
        return combinations
    }

    fun solver(nums: List<Int>, result : Long) : Boolean {
        val combinations: List<String> = getOpCombinations(listOf('*','+'), nums.size-1)
        //println("combinations")
        //combinations.println()
        var computedResult : Long = -1
        combinations.forEach {
            combo ->
                computedResult = -1
                for (idx in 0..nums.size-2) {
                    if (combo.length > 1 && combo[idx] == '*' || combo.equals("*")) {
                        if (computedResult.toInt() == -1) {
                            computedResult = nums[idx].toLong()
                        }
                        computedResult *= nums[idx+1]
                    } else if (combo.length > 1 && combo[idx] == '+' || combo.equals("+")) {
                        if (computedResult.toInt() == -1) {
                            computedResult = nums[idx].toLong()
                        }
                        computedResult += nums[idx+1]
                }
            }
            //println("computedResult ${computedResult}")
            if (computedResult == result) {
                return true
            }
        }
        return false
    }

    fun solverConcat(nums: List<Int>, result : Long) : Boolean {
        val combinations: List<String> = getOpCombinations(listOf('*','+','|'), nums.size-1)
        //println("combinations")
        //combinations.println()
        var computedResult : Long = -1
        combinations.forEach {
                combo ->
            computedResult = -1
            for (idx in 0..nums.size-2) {
                if (combo.length > 1 && combo[idx] == '*' || combo.equals("*")) {
                    if (computedResult.toInt() == -1) {
                        computedResult = nums[idx].toLong()
                    }
                    computedResult *= nums[idx+1]
                } else if (combo.length > 1 && combo[idx] == '+' || combo.equals("+")) {
                    if (computedResult.toInt() == -1) {
                        computedResult = nums[idx].toLong()
                    }
                    computedResult += nums[idx+1]
                } else if (combo[idx] == '|') {
                    if (computedResult.toInt() == -1) {
                        computedResult = nums[idx].toLong()
                    }
                    computedResult = (computedResult.toString() + nums[idx+1].toString()).toLong()
                }
            }
            //println("computedResult ${computedResult}")
            if (computedResult == result) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Long {
        //input.forEach { i -> i.println() }
        var sum: Long = 0;
        input.forEach{
            i ->
            val split = i.split(":")
            val result = split[0].toLong()
            val nums = split[1].trim().split(" ")
            val numInts = nums.map { j -> j.toInt() }
            //println(nums)
            val solved = solver(numInts, result)
            if (solved) {
                sum += result
                //sum.println()
            }

        }
        return sum;
    }

    fun part2(input: List<String>): Long {
        //input.forEach { i -> i.println() }
        var sum: Long = 0;
        input.forEach{
                i ->
            val split = i.split(":")
            val result = split[0].toLong()
            val nums = split[1].trim().split(" ")
            val numInts = nums.map { j -> j.toInt() }
            //println(nums)
            val solved = solverConcat(numInts, result)
            if (solved) {
                sum += result
                //sum.println()
            }

        }
        return sum;
    }

    val testInput = readInput("Day07")
    //val testInput = readInput("exampleInput");

    println("result part 1:")
    measureTime { part1(testInput).println()}.println()
    println("result part 2:")
    measureTime { part2(testInput.toMutableList()).println(); }.println()

}
