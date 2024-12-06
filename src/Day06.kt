import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.floor

fun main() {
    fun findStartPos(input: List<String>): Pair<Int, Int> {
        val row = input.indexOfFirst { i ->
            i.contains("^")
        }
        val col = input[row].indexOf("^")
        return Pair<Int, Int>(row, col)
    }

    fun nextDirection(direction: String): String {
        when (direction) {
            "up" -> return "right";
            "right" -> return "down"
            "down" -> return "left"
            "left" -> return "up"
        }
        return ""
    }

    fun part1(input: List<String>): Int {
        input.forEach { i -> i.println() }

        var (row, col) = findStartPos(input)
        println("startPos ${row} ${col}")
        var currentSymbol = '^'
        var visitedPos = mutableSetOf<Pair<Int, Int>>();
        var direction = "up";

        while (row in 0..input.size-1 && col in 0..input[row].length-1 ) {
            while (currentSymbol != '#') {
                try {
                    when (direction) {
                        "up" -> currentSymbol = input[--row][col];

                        "down" -> currentSymbol = input[++row][col];

                        "right" -> currentSymbol = input[row][++col];

                        "left" -> currentSymbol = input[row][--col];
                        else -> println("something went wrong")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    println("out of bounds")
                    currentSymbol = '#'
                    when (direction) {
                        "up" -> row -= 1;
                        "right" -> col += 1
                        "down" -> row += 1
                        "left" -> col -= 1
                    }
                }
                visitedPos.add(Pair(row, col))
            }
            // reset count and positions
            visitedPos.remove(Pair(row,col))
            when (direction) {
                "up" -> row += 1;
                "right" -> col -= 1
                "down" -> row -= 1
                "left" -> col += 1
            }
            currentSymbol = '<'
            direction = nextDirection(direction)
            println("direction changed to ${direction} and visitCount now ${visitedPos.size}")
        }
        println("currentPos ${row} ${col}")
        println("visits " + visitedPos.size)
        return visitedPos.size;
    }

    fun part2(input: List<String>): Int {
        return 0;
    }

    val testInput = readInput("Day06")
    //val testInput = readInput("exampleInput");

    println("result part 1:");
    part1(testInput).println();
    println("result part2")
    part2(testInput).println();
}
