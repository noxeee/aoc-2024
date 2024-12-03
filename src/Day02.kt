import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        // Or read a large test input from the `src/Day01_test.txt` file:
        val reportsLevels : MutableList<Int> = mutableListOf();
        //testInput.get(0).println();
        var safeReports = 0;
        var unsafeReports = 0;
        input.forEach {
            i -> val split = i.split(" ");
            split.forEach { i -> i.toInt() }

            println("split arr ${split}");
            println("split sorted ${split.sorted()}");
            println("split sortedDescending ${split.sortedDescending()}");

            if (split.equals(split.sorted()) or split.equals(split.sorted().reversed())) {
                val count = split.windowed(2).count{(first, second) ->
                    var difference = abs(first.toInt().minus(second.toInt()));
                    difference < 1 || difference > 3;
                }

                if (count > 0) {
                    unsafeReports += 1
                } else {
                    safeReports += 1;
                }

            } else {
                println("unsafe report: ${split}");
                unsafeReports += 1;
            }
        }
        println("unsafe reports: ${unsafeReports}");
        return safeReports;
    }

    fun safe(report: List<Int>) : Boolean {
        return report.windowed(2).all{(a,b) -> abs(a-b) in 1..3}
                && (report.sorted() == report || report.sorted().reversed() == report)
    }

    fun part2(input: List<String>): Int {
            // Or read a large test input from the `src/Day01_test.txt` file:
        return input.count { line ->
            val split = line.split(" ");
            var intList = split.map { i -> i.toInt()};
            if (!safe(intList)) {
                intList.forEachIndexed{
                        index, value ->
                    val reducedList : List<Int> = intList.take(index) + intList.drop(index + 1);
                    if (safe(reducedList)) intList = reducedList;
                }
            }
            safe(intList);
        }
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)
    val testInput = readInput("Day02")
    //val testInput = readInput("exampleInput");

    println("number of safe reports:");
    part1(testInput).println();
    part2(testInput).println();
}
