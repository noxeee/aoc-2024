import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        // Or read a large test input from the `src/Day01_test.txt` file:
        val leftList : MutableList<Int> = mutableListOf();
        val rightList : MutableList<Int> = mutableListOf();
        //testInput.get(0).println();
        input.forEach {
            i -> val split = i.split("\\s+".toRegex());
            leftList.add(split.get(0).toInt());
            rightList.add(split.get(1).toInt());
        }
//        print("Left list");
//        leftList.println();
//        print("right list");
//        rightList.println();

        leftList.sort();
        rightList.sort();

//        println("sorted list");
//        leftList.println();

        var sum : Int = 0;
        while (!leftList.isEmpty() and !rightList.isEmpty()) {
            var diff = leftList.first().minus(rightList.first());
//            println("diff with negatives");
//            println(diff);
            if (diff < 0) {
                diff *= -1;
            }
//            println("diff after * -1");
//            println(diff);
            sum = sum.plus(diff);
            rightList.removeFirst();
            leftList.removeFirst();
        }
        return sum;
    }

    fun part2(input: List<String>): Int {
            // Or read a large test input from the `src/Day01_test.txt` file:
            val leftList : MutableList<Int> = mutableListOf();
            val rightList : MutableList<Int> = mutableListOf();
            //testInput.get(0).println();
            input.forEach {
                    i -> val split = i.split("\\s+".toRegex());
                leftList.add(split.get(0).toInt());
                rightList.add(split.get(1).toInt());
            }
//            print("Left list");
//            leftList.println();
//            print("right list");
//            rightList.println();

            leftList.sort();
            rightList.sort();

//            println("sorted list");
//            leftList.println();

            var sum : Int = 0;
            while (!leftList.isEmpty()) {
                var occ = rightList.count{it == leftList.first()};
                sum = sum.plus(leftList.removeFirst() * occ);
            }
            return sum;
    }

    val testInput = readInput("Day01")
    //val testInput = readInput("exampleInput");
    println("final distance");
    measureTime { part1(testInput).println() }.println()
    measureTime { part2(testInput).println() }.println()
}
