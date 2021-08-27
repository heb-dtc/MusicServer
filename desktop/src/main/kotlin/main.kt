import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {

    MaterialTheme {

        // main window
        Row(modifier = Modifier.fillMaxSize()) {

            // Left panel
            Column(modifier = Modifier.padding(16.dp).weight(weight = 0.75f)) {

                // Radio list
                Column(
                    modifier = Modifier.border(
                        border = BorderStroke(1.dp, Color(0xff000000)),
                        shape = RoundedCornerShape(5.dp)
                    )
                ) {

                    Text(
                        text = "Radio",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )

                    Row(modifier = Modifier.padding(8.dp)) {
                        RadioItem()
                        RadioItem()
                        RadioItem()
                        RadioItem()
                        RadioItem()
                    }
                }

                // Podcast list
                Column(
                    modifier = Modifier.border(
                        border = BorderStroke(1.dp, Color(0xff000000)),
                        shape = RoundedCornerShape(5.dp)
                    )
                ) {

                    Text(
                        text = "Podcast",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )

                    Row(modifier = Modifier.padding(8.dp)) {
                        RadioItem()
                        RadioItem()
                        RadioItem()
                        RadioItem()
                        RadioItem()
                    }
                }
            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = Color.Black)

            Column(
                modifier = Modifier.padding(16.dp).fillMaxHeight().weight(0.25f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Player()
            }
        }
    }
}

@Composable
fun RadioItem() {
    Box(
        modifier = Modifier
            .height(140.dp)
            .width(140.dp)
            .padding(8.dp)
            .background(Color.Red)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
            .clickable {
                //onClick(radio)
            },
    ) {
        Text(
            "FRANCE INFO",
            Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
    }
}