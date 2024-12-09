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

    fun MutableList<Long>.emptySpaceIndexes(): List<Pair<Int, Int>> {
        val indexPairs = mutableListOf<Pair<Int, Int>>()
        var startIndex = 0
        var hasEmptySpace = false
        for (i in 1 until size) {
            if (get(i) != get(i - 1)) {
                if (hasEmptySpace) {
                    indexPairs.add(startIndex to (i - 1))
                }
                startIndex = i
                hasEmptySpace = false
            }
            if (get(i) == -1L) {
                hasEmptySpace = true
            }
        }
        println("indexPairs ${indexPairs}")
        return indexPairs
    }

    fun <T> swapChunks(list: MutableList<T>, start1: Int, end1: Int, start2: Int, end2: Int) {
        // Ensure the indices are within bounds and the chunks are of the same length
        if (start1 < 0 || end1 > list.size || start2 < 0 || end2 > list.size) {
            throw IllegalArgumentException("Invalid indices or chunks of different lengths")
        }
        if (start1 < start2) {
            var newEnd1 = end1
            // unpad the smaller chunk
            if ((end1 - start1) != (end2 - start2)) {
                newEnd1 = start1 + (end2 - start2)

                // Create copies of the chunks to avoid modifying the list during iteration
                val chunk1 = list.subList(start1, newEnd1 + 1).toMutableList()
                val chunk2 = list.subList(start2, end2 + 1).toMutableList()

                // Replace the original chunks with the swapped ones
                //list.subList(start1, newEnd1 + 1).clear()
                for (i in 0..newEnd1 - start1) {
                    list.subList(start1, newEnd1 + 1)[i] = chunk2[i]
                }

                for (i in 0..newEnd1 - start1) {
                    list.subList(start2, end2 + 1)[i] = chunk1[i]
                }

            } else {
                // Create copies of the chunks to avoid modifying the list during iteration
                val chunk1 = list.subList(start1, end1 + 1).toMutableList()
                val chunk2 = list.subList(start2, end2 + 1).toMutableList()

                // Replace the original chunks with the swapped ones
                for (i in 0..end1 - start1) {
                    list.subList(start1, end1 + 1)[i] = chunk2[i]
                }

                for (i in 0..end1 - start1) {
                    list.subList(start2, end2 + 1)[i] = chunk1[i]
                }
            }
        }
    }

    fun part2(input: List<String>): Long {
        var counter = 0
        var resultList = mutableListOf<Long>()
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

        for (i in resultList.last() downTo 0) {
            val blockIndex1 = resultList.indexOfFirst { value -> value == i.toLong() }
            val blockIndex2 = resultList.indexOfLast { value -> value == i.toLong() }
            //val block = resultList.subList(blockIndex1, blockIndex2)

            // find indexes of empty spaces
            val emptySpaceIndexes = resultList.emptySpaceIndexes().toList()
            var swapped = false
            emptySpaceIndexes.forEach { (idx1, idx2) ->
                if ((idx2 - idx1) >= (blockIndex2 - blockIndex1) && !swapped) {
                    swapChunks(resultList, idx1, idx2, blockIndex1, blockIndex2)
                    swapped = true
                }
            }
            swapped = false
        }

        println("after swapping")
        println(resultList)

        return resultList.withIndex().sumOf { (i, num) ->
            if (num.toInt() != -1) {
                num * i
            } else {
                0
            }
        }
    }

    val testInput = readInput("Day09")
    //val testInput = readInput("exampleInput");

    println("result part 1:")
    measureTime { part1(testInput).println() }.println()
    println("result part 2:")
    measureTime { part2(testInput.toMutableList()).println(); }.println()

}
