import kotlin.test.Test
import kotlin.test.assertEquals

class SolverTest {
    @Test
    fun CNFTests() {
        val testCNF = CNF("input/test1.cnf")
        assertEquals("(x1 + -x3)(x2 + x3 + -x1)", testCNF.toString())
        val testCNF2 = CNF("input/test2.cnf")
        assertEquals("(x1 + x2)(-x2 + -x4)(x3 + x4)(-x4 + -x5)(x5 + -x6)(x6 + -x7)(x6 + x7)(x7 + -x16)" +
                "(x8 + -x9)(-x8 + -x14)(x9 + x10)(x9 + -x10)(-x10 + -x11)(x10 + x12)(x11 + x12)" +
                "(x13 + x14)(x14 + -x15)(x15 + x16)", testCNF2.toString())
        val testCNF3 = CNF("input/uf20-0103.cnf")
        assertEquals("(x1 + x6 + -x8)(x5 + x10 + x17)(-x15 + -x10 + -x20)" +
                "(-x3 + x11 + -x18)(x10 + -x14 + x8)(x19 + -x15 + -x8)(-x19 + -x4 + x2)" +
                "(x3 + -x6 + x13)(-x2 + -x20 + x16)(x16 + x2 + x15)(-x5 + -x7 + x3)(x5 + x12 + -x6)(-x10 + -x3 + x2)" +
                "(-x13 + -x19 + x4)(x7 + -x12 + x19)(x14 + -x7 + -x10)(-x10 + -x11 + -x17)(x5 + -x7 + -x16)" +
                "(-x15 + x10 + x14)(x3 + -x8 + -x16)(-x9 + -x11 + x3)(x8 + x6 + x12)(-x18 + -x6 + -x15)(x9 + x8 + x2)" +
                "(x7 + -x9 + x11)(-x19 + -x18 + x2)(x4 + -x19 + x15)(-x5 + -x6 + x18)(x7 + -x6 + -x11)" +
                "(-x16 + -x1 + -x4)(x17 + x9 + x18)(-x8 + x1 + -x15)(-x20 + x6 + x15)(-x5 + x10 + x14)" +
                "(x10 + -x1 + -x9)(x4 + x20 + -x9)(-x3 + x12 + -x11)(x14 + x7 + x17)(x11 + x9 + -x10)" +
                "(-x10 + x12 + -x19)(x3 + x2 + -x6)(x7 + -x17 + -x3)(-x9 + -x15 + -x5)(x2 + -x5 + x19)" +
                "(-x16 + x7 + x6)(x17 + -x10 + x9)(-x8 + x12 + x11)(-x3 + -x4 + x11)(-x17 + -x4 + -x1)" +
                "(-x5 + x17 + x10)(-x3 + -x9 + -x8)(-x10 + -x5 + x4)(-x18 + x2 + -x3)(x20 + -x11 + x18)" +
                "(-x19 + -x6 + -x16)(x8 + -x19 + x17)(x7 + x18 + -x11)(-x18 + -x11 + -x20)(-x14 + -x10 + -x18)" +
                "(x14 + x11 + -x19)(x8 + -x18 + x9)(x12 + x5 + x20)(-x20 + -x3 + -x19)(x10 + -x7 + x15)" +
                "(x14 + -x9 + -x7)(-x11 + -x15 + x6)(x7 + x19 + -x8)(x7 + x6 + -x16)(-x2 + -x12 + -x20)" +
                "(-x4 + x7 + x6)(-x7 + -x20 + -x19)(x20 + x2 + -x10)(-x4 + x16 + x19)(-x11 + -x3 + x13)" +
                "(-x8 + -x2 + x12)(-x17 + -x19 + -x1)(-x7 + -x11 + x17)(-x9 + x2 + x7)(x11 + x5 + x1)(x9 + -x5 + -x17)" +
                "(-x20 + x15 + -x18)(-x1 + -x17 + -x11)(-x4 + -x16 + -x18)(-x18 + x6 + x4)(-x3 + -x15 + x11)" +
                "(-x17 + x16 + x2)(-x9 + x10 + -x5)(-x15 + x10 + -x12)(x18 + -x15 + -x16)(-x15 + -x11 + -x18)" +
                "(-x8 + x19 + -x9)", testCNF3.toString())
    }

    private fun solution(solve: Map<Int,Boolean>): String {
        if (solve.isEmpty()) return ("no solution")
        val sortedMap = solve.toSortedMap()
        val tempList = mutableListOf<String>()
        for((k,v) in sortedMap) {
            tempList.add("x${k + 1} = " + if (v) "1" else "0")
        }
        return tempList.joinToString(separator = ", ")
    }

    @Test
    fun solveTest() {
        val testCNF = CNF("input/test1.cnf") //3v 2c
        val testCNF2 = CNF("input/test2.cnf") //16v 18c
        val testCNF3 = CNF("input/uf20-0103.cnf") //20v 91c
        val testCNF4 = CNF("input/aim-50-1_6-yes1-4.cnf") //50v 80c

        val testSolve = testCNF.solve()
        val testSolve2 = testCNF2.solve()
        val testSolve3 = testCNF3.solve()
        val testSolve4 = testCNF4.solve()

        println(solution(testSolve.second))
        println(solution(testSolve2.second))
        println(solution(testSolve3.second))
        println(solution(testSolve4.second))

        assertEquals(true, testSolve.first)
        assertEquals(true, testSolve2.first)
        assertEquals(false, testSolve3.first)
        assertEquals(true, testSolve4.first)
    }

}