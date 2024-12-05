import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun as_matrix(input: List<String>): Array<Array<Char>> {
        val matrix : Array<Array<Char>> = Array(50) { arrayOf<Char>() };

        input.forEachIndexed{
            i, str ->
            matrix.set(i, str.toCharArray().toTypedArray());
        }
        return matrix;
        //matrix.forEach { i -> i.toList().println() }
    }

    fun count_forward(strings: List<String>): Int {
        var xmas_count = 0;
        strings.forEach {
            line ->
            xmas_count += "XMAS|SAMX".toRegex().findAll(line).count();
        }
        return xmas_count;
    }

    fun count_diagonal(matrix: Array<Array<Char>>): Int {
        var xmas_count = 0;
        var diagList : MutableList<String> = mutableListOf();
        for (i in 0 until matrix.size) {
            diagList.add(matrix[i][i].toString());
        }
        return xmas_count;
    }

    fun part1(input: List<String>): Int {
        val matrix = as_matrix(input);
        var sum = 0;
        sum += count_forward(input);
        sum += count_diagonal(matrix);
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
    //val testInput = Path("src/Day03.txt").readText().trim().lines();
    val testInput = readInput("exampleInput");

    //println(testInput);
    //testInput.forEach{i -> println("line "); i.println()};
    println("multiplication result:");
    //testInput.forEach{i -> part1(i).println()};
    part1(testInput).println();
    println("part2")
    //part2(testInputPartTwo).println();
    //part2(testInput.joinToString("")).println();
}
