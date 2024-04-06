package x.bod.eval

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Equations.forEachIndexed { index, it ->
            val exp1 = Expression(it.first)
            exp1.evaluate()
            if (exp1.result != "${it.second.toDouble()}") {
                println("${exp1.equation} != ${exp1.result}")
            } else println("Pass - ${index + 1}")
        }
    }
}

val Equations = arrayOf(
    "21.2 * 4.2" to 89.04,
    "(2.0001+1) * 4.2" to 12.600420000000002,
    "((2.0001+(3*2)) * 4.2)" to 33.60042,
    "(0.0001+(3*2 * 4.2))" to 25.200100000000003,
    "((0.0001+3*2) * 4.2)" to 25.20042,
    "((0.0001+3)*2 * 4.2)" to 25.200840000000003,
    "((0.0001+3)*2 * (4.2/9.1))" to 2.769323076923077,
    "((0.0001+3)*2 * (4.2+0.2/9.1))" to 25.33271252747253,
    "((0.0001+3*2) / (4.2+0.2/9.1))" to 1.4211585111920875,
    "((0.0001+3*2) + (4.2+0.2/9.1))" to 10.222078021978021,
    "((0.0001+3*2) % (4.2+0.2/9.1))" to 1.7781219780219777,
    "((0.0001+3%2) % (4.2(0.2/9.1)))" to 0.07702307692307686,
    "((0.0001+3%2) % (4.2*8(0.2/9.1)))" to 0.2616384615384615,
    "((0.0001+3%2) ^ (4.2*8(0.2/9.1)))" to 1.0000738451882063,
)