import kotlin.time.measureTime

fun main() {
    fun getCoordinates(input: List<String>): HashMap<Char, MutableList<Pair<Int, Int>>> {
        val coordsMap = hashMapOf<Char, MutableList<Pair<Int, Int>>>()
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
        val distance = Pair(pointA.first - pointB.first, pointA.second - pointB.second)
        val negAntinode = Pair(pointA.first + distance.first, pointA.second + distance.second)
        val posAntinode = Pair(pointB.first + distance.first * -1, pointB.second + distance.second * -1)
        println("pointA $pointA pointB $pointB distance $distance negAntinode $negAntinode posAntinode $posAntinode")
        return listOf(negAntinode, posAntinode)
    }

    fun Pair<Int, Int>.withinBounds(ySz: Int, xSz: Int): Boolean {
        return this.first in 0..<ySz && this.second in 0..<xSz
    }

    fun placeAntinodesEqually(
        pointA: Pair<Int, Int>,
        pointB: Pair<Int, Int>,
        inputSz: Int
    ): List<Pair<Int, Int>> {
        val antinodes = mutableListOf<Pair<Int, Int>>()
        val distance = Pair(pointA.first - pointB.first, pointA.second - pointB.second)

        // negative antinodes
        var negativeDist = distance
        while (Pair(pointA.first + negativeDist.first, pointA.second + negativeDist.second).withinBounds(
                inputSz,
                inputSz
            )
        ) {
            antinodes.add(Pair(pointA.first + negativeDist.first, pointA.second + negativeDist.second))
            negativeDist = Pair(negativeDist.first + distance.first, negativeDist.second + distance.second)
        }

        // positive antinodes
        var positiveDist = distance
        while (Pair(pointB.first + positiveDist.first * -1, pointB.second + positiveDist.second * -1).withinBounds(
                inputSz,
                inputSz
            )
        ) {
            //if (positiveDist == Pair(0, 0)) break
            antinodes.add(Pair(pointB.first + positiveDist.first * -1, pointB.second + positiveDist.second * -1))
            positiveDist =
                Pair(positiveDist.first - distance.first * -1, positiveDist.second - distance.second * -1)
        }

        //println("pointA ${pointA} pointB ${pointB} distance ${distance} negAntinode ${negAntinode} posAntinode ${posAntinode}")
        println("antinodes for combo $pointA, $pointB : $antinodes")
        return antinodes
    }

    // FIXME printing the places antinodes doesn't work
    fun createAntinodes(input: List<String>, antinodes: List<Pair<Int, Int>>) {
        val mutableInput = input.toMutableList()
        antinodes.forEach { node ->
            val str = StringBuilder(mutableInput[node.first])
            println("before ${mutableInput[node.first]} toString $str")
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
        var sum = 0
        // create a map of coordinates
        val coordsMap = getCoordinates(input)
        val antinodes = mutableListOf<Pair<Int, Int>>()
        coordsMap.forEach { (char, coordList) ->
            if (char != '.') {
                val combos = combos(coordList)
                println("combos $combos for $char")
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
        println("ANTINODES: $antinodes sz ${antinodes.distinct().size}")
        //createAntinodes(input, antinodes)
        return antinodes.distinct().size
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        // create a map of coordinates
        val coordsMap = getCoordinates(input)
        val antinodes = mutableListOf<Pair<Int, Int>>()
        coordsMap.forEach { (char, coordList) ->
            if (char != '.') {
                val combos = combos(coordList)
                println("combos $combos for $char")
                combos.forEach { coordPair ->
                    sum += placeAntinodesEqually(
                        coordPair[0],
                        coordPair[1], input.size
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
        println("ANTINODES: $antinodes sz ${antinodes.distinct().size}")
        //createAntinodes(input, antinodes)
        val antennas = coordsMap.filterKeys { key -> key != '.' }.values.flatten()
        println("antennas ${antennas.size}")
        return (antinodes + antennas).distinct().size
    }

    val testInput = readInput("Day08")
    //val testInput = readInput("exampleInput");

    println("result part 1:")
    measureTime { part1(testInput).println() }.println()
    println("result part 2:")
    measureTime { part2(testInput.toMutableList()).println(); }.println()

}
