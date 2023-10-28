package com.example.kotlindz1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
                    MyScreenTest()
                }
            }
        }
    }
}

@Composable
fun MyScreenTest() {
    val rectangles = rememberSaveable { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val orientation = LocalConfiguration.current.orientation

        val columnCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4

        val tmp: MutableList<Int> = mutableListOf()
        if(rectangles.value != 0) {

            for (i in 0 until rectangles.value) {
                tmp.add(i)
            }
        }
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
                    ) {
                        Text(
                            text = (number + 1).toString(),
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
            }

        }

        Button(
            onClick = {
                rectangles.value++
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Добавить прямоугольник")
        }
    }
}


