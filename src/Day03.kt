import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.time.times

fun main() {
    fun solve(mulStr: String) {

    }

    fun part1(input: String): Int {
        input.println();
        val regex = """mul\((?<num1>\d+),(?<num2>\d+)\)""".toRegex()
        val matchResult = regex.findAll(input.trim());
        var sum = 0;
        if (matchResult.none()) println("no matchresult");
        println("number of matches for part 1: ${matchResult.count()}")
        for (matchResult1 in matchResult) {
            val num1 = matchResult1.groups["num1"]?.value?.toIntOrNull();
            val num2 = matchResult1.groups["num2"]?.value?.toIntOrNull();
            if (num1 != null && num2 != null) {
                sum += num1 * num2;
            }
        }
        return sum;
    }

    fun part2(input: String): Int {
        input.println();
        var sum = 0;
        // handle first part
//        val split = """do""".toRegex().split(input);
//        println("split ${split}");
//        sum += part1(split.get(0));

        val regex = """mul\((?<num1>\d+),(?<num2>\d+)\)|do\(\)|don't\(\)""".toRegex()
        val matchResult = regex.findAll(input);
        var do_mul: Boolean = true;
        if (matchResult.none()) println("no matchresult");
        println("number of matches: ${matchResult.count()}")
        for (matchResult1 in matchResult) {
            if (matchResult1.value.contains("don't()")) {
                do_mul = false;
            } else if (matchResult1.value.contains("do()")) {
                do_mul = true;
            }
            if (matchResult1.value.contains("mul") && do_mul) {
                val num1 = matchResult1.groups["num1"]?.value?.toIntOrNull();
                val num2 = matchResult1.groups["num2"]?.value?.toIntOrNull();
                println("match: ${matchResult1.groupValues}");
                if (num1 != null && num2 != null) {
                    sum += num1 * num2;
                }
            }

        }
        return sum;
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)
    val testInput = Path("src/Day03.txt").readText().trim().lines();
    //val testInput = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    val testInputPartTwo = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
    //println(testInput);
    //testInput.forEach{i -> println("line "); i.println()};
    println("multiplication result:");
    //testInput.forEach{i -> part1(i).println()};
    part1(testInput.joinToString("")).println();
    println("part2")
    part2(testInputPartTwo).println();
    part2(testInput.joinToString("")).println();
}
