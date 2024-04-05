package x.bod.claclac

import Expression
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import x.bod.claclac.components.ButtonCalc
import x.bod.claclac.ui.theme.ClaclacTheme
import kotlin.math.log2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClaclacTheme {
                CalculatorScreen()
            }
        }
    }
}

@Preview
@Composable
fun CalculatorScreen() {
    val context = LocalContext.current
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val endWithDigit: () -> Boolean =
        { expression.isNotEmpty() && Regex("[0-9]").matches("${expression.last()}") }
    Column(
        modifier = Modifier
            .background(Color(0xFF292C30))
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                textAlign = TextAlign.Right,
                text = expression,
                fontSize = (5 / log2(expression.length + 1.0) * 40).sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = (5 / log2(expression.length + 1.0) * 40).sp
            )
            if (result.isNotEmpty())
                Text(
                    textAlign = TextAlign.Right,
                    text = result,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    lineHeight = 35.sp
                )
        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(modifier = Modifier.weight(1f)) {
                ButtonCalc("C") {
                    expression = ""
                    result = ""
                }
                ButtonCalc("7") { expression += it }
                ButtonCalc("4") { expression += it }
                ButtonCalc("1") { expression += it }
                ButtonCalc("%") { expression += it }
            }
            Column(modifier = Modifier.weight(1f)) {
                ButtonCalc("✘") { if (expression.isNotEmpty()) expression = expression.dropLast(1) }
                ButtonCalc("8") { expression += it }
                ButtonCalc("5") { expression += it }
                ButtonCalc("2") { expression += it }
                ButtonCalc("0") { expression += it }
            }
            Column(modifier = Modifier.weight(1f)) {
                ButtonCalc("/", primary = true) {
                    if (endWithDigit()) expression += it
                }
                ButtonCalc("9") { expression += it }
                ButtonCalc("6") { expression += it }
                ButtonCalc("3") { expression += it }
                ButtonCalc(".") {
                    if (endWithDigit()) expression += it
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                ButtonCalc("*", primary = true) {
                    if (endWithDigit()) expression += it
                }
                ButtonCalc("-", primary = true) {
                    expression += it
                }
                ButtonCalc("+", primary = true) {
                    expression += it
                }
                ButtonCalc("=", 0.5f, true) {
                    try {
                        val exp = Expression(expression).apply { this.evaluate() }
                        result = exp.result
                    } catch (e: Throwable) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
