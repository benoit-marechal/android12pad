package com.example.pad

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
    fun getSoundResourceId(context: MainActivity, row: Int, column: Int): Int {
        val resourceName = "click_sound_${column}_${row}"
        return context.resources.getIdentifier(resourceName, "raw", context.packageName)
    }

    private fun onGridButtonClick(row: Int, column: Int) {
        Toast.makeText(this, "Button clicked at row $row and column $column", Toast.LENGTH_SHORT).show()

        val resourceId = getSoundResourceId(this, row, column)
        if (resourceId != 0) { // Resource found
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
            mediaPlayer = MediaPlayer.create(this, resourceId)
            mediaPlayer?.start() // Play the sound
        } else {
            // Handle the case where the resource is not found
            Log.e("SoundError", "Resource not found for row $row and column $column")
        }

        // Play the sound
        // mediaPlayer = MediaPlayer.create(this, R.raw.click_sound)


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
    onButtonClick: (row: Int, column: Int) -> Unit
) {
    val numberOfRows = 3
    val numberOfColumns = 3

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        // Utilise fillMaxSize() pour que la colonne occupe tout l'espace disponible.
        for (row in 0 until numberOfRows) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
                // Utilise weight(1f, true) pour que chaque rangée prenne une part égale de l'espace vertical.
            ) {
                for (column in 0 until numberOfColumns) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                        // Utilise fillMaxHeight() pour que la boîte occupe tout l'espace vertical disponible dans la rangée.
                    ) {
                        Button(
                            onClick = { onButtonClick(row, column) },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                                shape = RoundedCornerShape(10.dp)
                            // Utilise fillMaxSize() pour que le bouton occupe toute la taille de la boîte.
                        ) {
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

