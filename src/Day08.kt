import kotlin.time.measureTime

fun main() {
    fun getCoordinates(input: List<String>): HashMap<Char, MutableList<Pair<Int, Int>>> {
        var coordsMap = hashMapOf<Char, MutableList<Pair<Int, Int>>>()
        input.forEachIndexed { rowNum, row ->
            row.forEachIndexed { colNum, col ->
                if (coordsMap[col].isNullOrEmpty()) {
                    coordsMap[col] = mutableListOf()
                }
                coordsMap[col]?.add(Pair(rowNum, colNum))
            }
        }
        coordsMap.println()
        return coordsMap
    }

    fun placeAntinodes(
        pointA: Pair<Int, Int>,
        pointB: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        var distance = Pair(pointA.first - pointB.first, pointA.second - pointB.second)
        var negAntinode = Pair(pointA.first + distance.first, pointA.second + distance.second)
        var posAntinode = Pair(pointB.first + distance.first * -1, pointB.second + distance.second * -1)
        println("pointA ${pointA} pointB ${pointB} distance ${distance} negAntinode ${negAntinode} posAntinode ${posAntinode}")
        return listOf(negAntinode, posAntinode)
    }

    fun Pair<Int, Int>.withinBounds(ySz: Int, xSz: Int): Boolean {
        return this.first in 0..ySz - 1 && this.second in 0..xSz - 1
    }

    fun createAntinodes(input: List<String>, antinodes: List<Pair<Int, Int>>) {
        var mutableInput = input.toMutableList()
        antinodes.forEach { node ->
            val str = StringBuilder(mutableInput[node.first])
            println("before ${mutableInput[node.first]} toString ${str}")
            mutableInput[node.first] = str.setCharAt(node.second, '#').toString()
        }
        mutableInput.forEach { i ->
            i.println()
        }
    }

    fun combos(input: MutableList<Pair<Int, Int>>): List<List<Pair<Int, Int>>> {
        return input.indices.flatMap { i ->
            input.drop(i + 1).map { j -> listOf(input[i], j) }
        }
    }

    fun part1(input: List<String>): Int {
        input.forEach { i -> i.println() }
        println("input size y rows ${input.size} x cols ${input[0].length}")
        var sum: Int = 0;
        // create a map of coordinates
        var coordsMap = getCoordinates(input)
        var antinodes = mutableListOf<Pair<Int, Int>>()
        coordsMap.forEach { (char, coordList) ->
            if (char != '.') {
                val combos = combos(coordList)
                println("combos ${combos} for ${char}")
                combos.forEach { coordPair ->
                    sum += placeAntinodes(
                        coordPair[0],
                        coordPair[1]
                    ).also { nodes ->
                        antinodes.addAll(nodes.filter { pair ->
                            pair.withinBounds(input.size, input[0].length)
                        })
                    }.count { pair ->
                        pair.withinBounds(input.size, input[0].length)
                    }
                }
            }
        }
        println("ANTINODES: ${antinodes} sz ${antinodes.distinct().size}")
        //createAntinodes(input, antinodes)
        return antinodes.distinct().size;
    }

    fun part2(input: List<String>): Long {
        //input.forEach { i -> i.println() }
        var sum: Long = 0;
        return sum;
    }

    val testInput = readInput("Day08")
    //val testInput = readInput("exampleInput");

    println("result part 1:")
    measureTime { part1(testInput).println() }.println()
    println("result part 2:")
    measureTime { part2(testInput.toMutableList()).println(); }.println()

}
