import kotlin.math.floor
import kotlin.time.measureTime

fun main() {
    fun get_middle_page(pages: List<Int>): Int {
        return pages.get(floor(((pages.size / 2).toDouble())).toInt());
    }

    fun rules_apply(pages: List<Int>, rules: List<List<Int>>): Boolean {
        for (rule in rules) {
            val firstNum = rule.get(0);
            val secondNum = rule.get(1);
            if (pages.contains(firstNum) && pages.contains(secondNum)) {
                if (pages.indexOf(firstNum) > pages.indexOf(secondNum)) {
                    return false;
                }
            }
        }
        return true;
    }

    fun part1(input: List<String>): Int {
//        input.println();
        val endIndex = input.indexOfFirst { i -> i.isEmpty() }
        val ordering_rules = input.subList(0, endIndex).map { i ->
            i.split("|").map { j -> j.toInt() }
        }
        val pages = input.subList(endIndex + 1, input.size).map { i ->
            i.split(",").map { j -> j.toInt() }
        };
        ordering_rules.println()
        pages.println()

        // get the correct pages according to rules
        val filtered_pages = pages.filter { p ->
            rules_apply(p, ordering_rules);
        }
        println("FILTERED PAGES")
        filtered_pages.println()
        return filtered_pages.sumOf { i -> get_middle_page(i) }
    }

    fun getSecondIdx(num: Int): Int {
        return if (num == 1) 0 else 1;
    }

    fun order(pages: List<Int>, ordering_rules: List<List<Int>>): List<Int> {
        var pagesMut = pages.toMutableList()

        while (!rules_apply(pagesMut.toList(), ordering_rules)) {
            ordering_rules.forEach { rule ->
                if (pagesMut.contains(rule.get(0)) && pagesMut.contains(rule.get(1))) {
                    if (pagesMut.indexOf(rule.get(0)) > pagesMut.indexOf(rule.get(1))) {
                        pagesMut[pagesMut.indexOf(rule.get(0))] = rule.get(1);
                        pagesMut[pagesMut.indexOf(rule.get(1))] = rule.get(0);
                    }
                }
            }
        }

//        pagesMut.println()
        return pagesMut.toList();
    }

    fun part2(input: List<String>): Int {
//        input.println();
        val endIndex = input.indexOfFirst { i -> i.isEmpty() }
        val ordering_rules = input.subList(0, endIndex).map { i ->
            i.split("|").map { j -> j.toInt() }
        }
        val pages = input.subList(endIndex + 1, input.size).map { i ->
            i.split(",").map { j -> j.toInt() }
        };
//        ordering_rules.println()
//        pages.println()

        // get the correct pages according to rules
        val filtered_pages = pages.filter { p ->
            rules_apply(p, ordering_rules);
        }

//        println("FILTERED PAGES")
//        pages.println()
//        pages.minus(filtered_pages).println()

        // order the incorrect ones
        println("ORDERED PAGES")
        return pages.minus(filtered_pages).sumOf { i -> get_middle_page(order(i, ordering_rules)) }
    }

    val testInput = readInput("Day05")
    //val testInput = readInput("exampleInput");

    println("result part 1:");
    measureTime { part1(testInput).println() }.println()
    println("result part2")
    measureTime { part2(testInput).println() }.println()
}
