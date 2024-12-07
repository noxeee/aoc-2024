import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.floor

fun main() {

    fun part2(input: MutableList<String>, visitedPos: Set<Pair<Int,Int>>): Int {
        var loops = 0;
        return loops;
    }

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

    fun getOpCombinations(numSz: Int): List<String> {
        val operators = listOf('*', '+')
        var combinations = mutableListOf<String>()
//        for (len in 0..numSz) {
//            val combo = genCombinations(operators, len)
//            combinations.addAll(combo)
//        }
        val combo = genCombinations(operators, numSz)
        combinations.addAll(combo)
        return combinations
    }

    fun solver(nums: List<Int>, result : Long) : Boolean {
        val combinations: List<String> = getOpCombinations(nums.size-1)
        println("combinations")
        combinations.println()
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
            println("computedResult ${computedResult}")
            if (computedResult == result) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Long {
        input.forEach { i -> i.println() }
        var sum: Long = 0;
        input.forEach{
            i ->
            val split = i.split(":")
            val result = split[0].toLong()
            val nums = split[1].trim().split(" ")
            val numInts = nums.map { j -> j.toInt() }
            println(nums)
            val solved = solver(numInts, result)
            if (solved) {
                sum += result
                sum.println()
            }

        }
        return sum;
    }


    val testInput = readInput("Day07")
    //val testInput = readInput("exampleInput");

    println("result part 1:");
    part1(testInput).println();
    println("result part2")
    //part2(testInput.toMutableList()).println();
}
