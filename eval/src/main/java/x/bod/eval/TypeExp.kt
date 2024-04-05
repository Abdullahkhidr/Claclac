package x.bod.eval

import kotlin.math.pow

internal sealed class TypeExp {
    companion object {
        fun getType(symbol: String, a: Double, b: Double): TypeExp {
            return when (symbol) {
                "^" -> POWER(a, b)
                "*" -> MULTI(a, b)
                "/" -> DIV(a, b)
                "%" -> REMAINDER(a, b)
                "+" -> SUM(a, b)
                "-" -> MINUS(a, b)
                else -> UnKnown()
            }
        }
    }

    open val value: Double = 0.0

    class MULTI(private val a: Double, private val b: Double) : TypeExp() {
        override val value: Double get() = a.times(b)
    }

    class DIV(private val a: Double, private val b: Double) : TypeExp() {
        override val value: Double get() = a.div(b)
    }

    class SUM(private val a: Double, private val b: Double) : TypeExp() {
        override val value: Double get() = a.plus(b)
    }

    class MINUS(private val a: Double, private val b: Double) : TypeExp() {
        override val value: Double get() = a.minus(b)
    }

    class POWER(private val a: Double, private val b: Double) : TypeExp() {
        override val value: Double get() = a.pow(b)
    }

    class REMAINDER(private val a: Double, private val b: Double) : TypeExp() {
        override val value: Double get() = a.rem(b)
    }

    class UnKnown : TypeExp() {
        override val value: Double get() = 0.0
    }
}