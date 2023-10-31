package com.example.kotlindz1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlindz1.ui.theme.KotlinDz1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinDz1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val rectangles = rememberSaveable { mutableStateOf(0) }
                    val phase = rememberSaveable { mutableStateOf(-1) }
                    if (phase.value == -1) {
                        SquareList(
                            rectangles.value,
                            { rectangles.value++ },
                            { number -> phase.value = number })
                    } else {
                        ShowNumber(number = phase.value) { phase.value = -1 }
                    }
                }
            }
        }
    }
}

@Composable
fun SquareList(rectangles: Int, onClickListener: () -> Unit, onSquareClickListener: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val orientation = LocalConfiguration.current.orientation

        val columnCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4

        val tmp: MutableList<Int> = MakeList(rectangles)

        MakeSquares(tmp, columnCount, onSquareClickListener)

        Button(
            onClick = {
                onClickListener()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Добавить прямоугольник")
        }
    }
}

fun MakeList(rectangles: Int): MutableList<Int> {
    val tmp: MutableList<Int> = mutableListOf()
    if (rectangles != 0) {
        for (i in 0 until rectangles) {
            tmp.add(i)
        }
    }
    return tmp
}

@Composable
fun MakeSquares(tmp: MutableList<Int>, columnCount: Int, onClickListener: (Int) -> Unit) {
    val chunkedRectangles = tmp.chunked(columnCount)
    for (row in chunkedRectangles) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (index in row.indices) {
                val number = row[index]
                val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .background(backgroundColor)
                        .clickable {
                            onClickListener(number)
                        }
                ) {
                    Text(
                        text = number.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun ShowNumber(number: Int, onClickListener: () -> Unit) {
    val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable {
                onClickListener()
            }
    ) {
        Box() {
            Text(text = "$number")
        }
    }
}


