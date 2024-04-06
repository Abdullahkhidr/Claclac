package x.bod.eval

class Expression(val equation: String) {
    var result: String = ""
        private set
    private val numExp = "([-+]?[0-9]+\\.?[0-9]*)"

    fun evaluate() {
        if (result.isEmpty())
            result = _evaluate(equation)
    }

    private tailrec fun _evaluate(subExp: String): String {
        cleanExp(subExp).apply {
            if (subExp != this) return _evaluate(this)
        }
        val group = Regex("\\([0-9.+\\-*/^%]+\\)").find(subExp)
        val powExp = Regex("$numExp(\\^)$numExp").find(subExp)
        val multiExp = Regex("$numExp(\\*)$numExp").find(subExp)
        val divExp = Regex("$numExp(/)$numExp").find(subExp)
        val remExp = Regex("$numExp(%)$numExp").find(subExp)
        val plusExp = Regex("$numExp(\\+)$numExp").find(subExp)
        val minusExp = Regex("$numExp(-)$numExp").find(subExp)

        val expressions = mutableListOf<MatchResult>()

        if (group != null) {
            group.groups[0]!!.range.apply {
                val range = first + 1..<last
                return _evaluate(
                    subExp.replaceRange(
                        group.groups[0]!!.range,
                        _evaluate(subExp.substring(range))
                    )
                )
            }
        }

        /**Check if there is Exponential x.bod.eval.Expression, Then evaluate it*/
        if (powExp != null) return _evaluate(subExp.replaceRange(powExp.range, calcExp(powExp)))

        /**Add The Same Priority Expressions In Array and sort By Index*/
        if (divExp != null) expressions.add(divExp)
        if (multiExp != null) expressions.add(multiExp)
        if (remExp != null) expressions.add(remExp)
        expressions.sortBy { it.range.first }

        if (expressions.isEmpty()) {
            if (plusExp != null) expressions.add(plusExp)
            if (minusExp != null) expressions.add(minusExp)
            expressions.sortBy { it.range.first }
        }

        if (expressions.isNotEmpty())
            return _evaluate(
                subExp.replaceRange(
                    expressions.first().range,
                    calcExp(expressions.first())
                )
            )
        try {
            return subExp.toDouble().toString()
        } catch (e: Exception) {
            throw ArithmeticException("Invalid Syntax ${e.message}")
        }
    }

    private fun calcExp(exp: MatchResult): String {
        TypeExp.getType(
            exp.groups[2]!!.value,
            exp.groups[1]!!.value.toDouble(),
            exp.groups[3]!!.value.toDouble()
        ).value
            .apply { return if (this > 0) "+$this" else "$this" }
    }

    private tailrec fun cleanExp(e: String): String {
        if (e.contains(" ")) return cleanExp(e.replace(" ", ""))
        val signsPos = mutableListOf<Int>()
        e.indexOf("++").apply { if (this != -1) signsPos.add(this) }
        e.indexOf("--").apply { if (this != -1) signsPos.add(this) }
        e.indexOf("+-").apply { if (this != -1) signsPos.add(this) }
        e.indexOf("-+").apply { if (this != -1) signsPos.add(this) }
        signsPos.sortBy { it }
        return if (signsPos.isEmpty()) {
            var pos = Regex("[0-9]\\(").find(e)?.range?.first
            if (pos == null) pos = Regex("\\)[0-9]").find(e)?.range?.first
            if (pos != null) {
                return cleanExp(e.replaceRange(pos..pos, "${e[pos]}*"))
            } else return e
        } else {
            val range = signsPos.first()..signsPos.first() + 1
            when (e.substring(range)) {
                "++" -> cleanExp(e.replaceRange(range, "+"))
                "--" -> cleanExp(e.replaceRange(range, "+"))
                "-+" -> cleanExp(e.replaceRange(range, "-"))
                else -> cleanExp(e.replaceRange(range, "-"))
            }
        }
    }
}