import kotlin.time.measureTime

fun main() {
    fun MutableList<Long>.addNtimes(n: Int, toAdd: Long) {
        for (i in 0..<n) {
            add(toAdd)
        }
    }

    fun lastNonSpaceChar(swappedStr: String): Char {
        return swappedStr.last { ch -> ch != '.' }
    }

    fun MutableList<Long>.lastNonSpaceNum(): Long {
        return last { int -> int.toInt() != -1 }
    }

    fun List<Int>.checksum() = foldIndexed(0L) { index, acc, i ->
        acc + index * i
    }

    fun part1(input: List<String>): Long {
        //input.forEach { i -> i.println() }
        println("input size y rows ${input.size} x cols ${input[0].length}")
        var counter = 0
        val resultList = mutableListOf<Long>()
        input[0].forEachIndexed { i, value ->
            if (i % 2 == 0) {
                resultList.addNtimes(value.digitToInt(), counter.toLong())
                counter++
            } else {
                resultList.addNtimes(value.digitToInt(), (-1).toLong()); // -1 representing empty space
            }
        }
        println("before swapping")
        println(resultList)
        resultList.forEachIndexed { index, value ->
            if (value.toInt() == -1) {
                resultList[index] = resultList.lastNonSpaceNum()
                resultList[resultList.lastIndexOf(resultList.lastNonSpaceNum())] = -1
            }
        }

//        // swap the spaces
//        var swappedStr = buildString {
//            resultList.forEach { digit ->
//                if (digit == -1) {
//                    append('.')
//                } else {
//                    append(digit.toChar())
//                }
//            }
//        }
//        swappedStr.forEachIndexed { i, value ->
//            if (value == '.') {
//                swappedStr = buildString {
//                    append(swappedStr)
//                    setCharAt(i, lastNonSpaceChar(swappedStr))
//                }
//                swappedStr = buildString {
//                    append(swappedStr)
//                    setCharAt(swappedStr.lastIndexOf(lastNonSpaceChar(swappedStr)), '.')
//                }
//            }
//        }
        println("after swapping")
        println(resultList)
        // 430631114 too low?
        return resultList.withIndex().sumOf { (i, num) ->
            if (num.toInt() != -1) {
                num * i
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Long {
        var sum: Long = 0
        return sum
    }

    val testInput = readInput("Day09")
    //val testInput = readInput("exampleInput");

    println("result part 1:")
    measureTime { part1(testInput).println() }.println()
    println("result part 2:")
    measureTime { part2(testInput.toMutableList()).println(); }.println()

}
