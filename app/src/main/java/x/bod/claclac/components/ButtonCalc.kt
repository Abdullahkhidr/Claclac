package x.bod.claclac.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonCalc(
    symbol: String = "0",
    aspectRatio: Float = 1f,
    primary: Boolean = false,
    onClick: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .aspectRatio(aspectRatio)
            .padding(2.dp)
            .background(
                Color(if (!primary) 0xFF323538 else 0xFF30AC6B),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick(symbol)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(symbol, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}