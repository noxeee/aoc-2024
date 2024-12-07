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

    fun walk(input: List<String>): Set<Pair<Int,Int>> {
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
        return visitedPos;
    }

    fun findLoops(input: List<String>): Boolean {
        var (row, col) = findStartPos(input)
        val startPos = Pair(row,col);
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
                // if all positions were visited before?? TODO
                if (startPos == Pair(row, col)) {
                    println("start pos equal to ${Pair(row, col)}");
                    return true
                };
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
        return false;
    }

    fun part2(input: MutableList<String>, visitedPos: Set<Pair<Int,Int>>): Int {
        var loops = 0;
        visitedPos.forEach { (row, col) ->
            if (Pair(row,col) != findStartPos(input.toList())) {
                input[row] = input[row].replaceRange(col, col+1, "#")
                if (findLoops(input)) loops++
            }
        }
        return loops;
    }

    fun part1(input: List<String>): Int {
        input.forEach { i -> i.println() }
        val visitedPos = walk(input)
        println("part 2 ${part2(input.toMutableList(), visitedPos)}")
        return visitedPos.size;
    }


    //val testInput = readInput("Day06")
    val testInput = readInput("exampleInput");

    println("result part 1:");
    part1(testInput).println();
    println("result part2")
    //part2(testInput.toMutableList()).println();
}
