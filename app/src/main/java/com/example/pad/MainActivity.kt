package com.example.pad

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pad.ui.theme.PADTheme

import androidx.compose.foundation.layout.*
// import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button


class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PADTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Benoit") { row, column ->
                        onGridButtonClick(row, column)
                    }
                }
            }
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound)

    }

    private fun onGridButtonClick(row: Int, column: Int) {
        // Play the sound
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(this, R.raw.click_sound)
        }
        mediaPlayer.start()

        // Example action
        Toast.makeText(this, "Button clicked at row $row and column $column", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer resource when the activity is destroyed
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onButtonClick: (row: Int, column: Int) -> Unit // Specify the types here
) {
    val numberOfRows = 3
    val numberOfColumns = 3

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        for (row in 0 until numberOfRows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (column in 0 until numberOfColumns) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                    ) {
                        Button(onClick = { onButtonClick(row, column) }) {
                            Text(text = "Button $row$column")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PADTheme {
        Greeting("Android") { row, column ->
            // Dummy action for preview
            println("Preview: Button clicked at row $row and column $column")
        }
    }
}

