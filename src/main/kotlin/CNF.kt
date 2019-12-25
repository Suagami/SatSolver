import java.io.File
import java.lang.NumberFormatException
import kotlin.math.*

class CNF() {

    private var clausesList = mutableListOf<Map<Int, Boolean>>()
    private var vars = 0

    constructor(inputName: String): this() {
        var iter = 0
        for(line in File(inputName).readLines()) when {
                line[0] == 'p' -> {
                    vars = line.split(' ')[2].toInt()
                    val n = line.split(' ')[3].toInt()
                    for (i in 0 until n) clausesList.add(mutableMapOf())
                }
                line[0] != 'c' -> {
                    val tempClause = mutableMapOf<Int, Boolean>()
                    for (item in line.split(' ')) {
                        try {
                            val temp = item.toInt()
                            if (temp != 0) tempClause[abs(temp) - 1] = temp > 0
                        } catch (e: NumberFormatException) {
                            continue
                        }
                    }
                    clausesList[iter] = tempClause
                    iter++
                }
        }
    }
    override fun toString(): String = buildString {
            for(clause in clausesList) {
                val tempList = mutableListOf<String>()
                for ((index, sign) in clause) {
                    if (!sign) tempList.add("-x${index + 1}")
                    else tempList.add("x${index + 1}")
                }
                append(tempList.joinToString(separator=" + ", prefix="(", postfix = ")"))
            }
    }

    fun solve(): Pair<Boolean,Map<Int,Boolean>> = solve(-1, mapOf())

    private fun solve(currLine: Int, currSolution: Map<Int,Boolean>): Pair<Boolean, Map<Int,Boolean>>{
        val tempLine = currLine + 1
        var count = 0
        for ((key, _) in clausesList[tempLine])
            if (!currSolution.containsKey(key)) count++
        if (count != 0) {
            for (i in 0 until pow2(count)) {
                val temp = i.toString(2).padStart(count,'0')
                val line = temp.toCharArray().map{it=='1'}
                var iter = 0
                var sum = false
                loop@ for ((key, value) in clausesList[tempLine]) when {
                    currSolution.containsKey(key) ->
                        if(value == currSolution[key]) {
                            sum = true
                            break@loop
                        }
                    else -> {
                        if(value == line[iter]) {
                            sum = true
                            break@loop
                        }
                        iter++
                    }
                }
                if (sum) {
                    val tempSol = currSolution.toMutableMap()
                    iter = 0
                    for ((key, _) in clausesList[tempLine])
                        if (!tempSol.containsKey(key)) {
                            tempSol[key] = line[iter]
                            iter++
                        }
                    if (tempSol.size < vars) {
                        val pair = solve(tempLine, tempSol)
                        if (pair.first) return pair
                    }
                    else return (checkSolution(tempSol) to tempSol)
                }
            }
        }
        else {
            val tempSol = currSolution.toMutableMap()
            for ((key, value) in clausesList[tempLine]) {
                if (value == tempSol[key]) {
                    if (tempSol.size < vars){
                        val pair = solve(tempLine, tempSol)
                        if (pair.first) return pair
                    }
                    else return (checkSolution(tempSol) to tempSol)

                }
            }
        }
        return (false to currSolution)
    }

    private fun checkSolution(solution: Map<Int, Boolean>): Boolean {
        for (clause in clausesList) {
            var sum = false
            for ((key, value) in clause) {
                if (solution[key] == value) {
                    sum = true
                    break
                }
            }
            if(!sum) return false
        }
        return true
    }
    private fun pow2(power:Int):Int{
        var out = 1
        for(i in 1..power) {
            out*=2
        }
        return out
    }
}